# System Symulacji Inteligentnych Świateł Drogowych

System symulacji inteligentnych świateł drogowych na skrzyżowaniu z czterema drogami dojazdowymi (północ, południe, wschód, zachód).

## Architektura

System składa się z dwóch głównych modułów:

### 1. simulation-algorithm
Zawiera algorytm sterowania światłami oraz modele danych:
- **TrafficPhase** - enum z 6 predefiniowanymi fazami świateł
- **TrafficLightControllerImpl** - implementacja algorytmu sterowania
- **IntersectionSimulator** - symulator skrzyżowania
- **Modele** - Car, Road, Intersection, TrafficLight, PedestrianLight, etc.

### 2. simulation-core
Spring Boot API z następującymi komponentami:
- **Kontrolery REST** - SimulationController, FileController, MonitoringController
- **Serwisy** - SimulationService, FileService, MonitoringService
- **DTO** - obiekty transferu danych dla API

## Algorytm Sterowania Światłami

Algorytm działa w oparciu o następujące zasady:

1. **Priorytet dla dróg z >5 aut** - drogi z więcej niż 5 autami mają pierwszeństwo
2. **Inteligentny wybór faz** - algorytm wybiera fazę, która obsłuży najwięcej ruchu
3. **Bezpieczeństwo** - unika konfliktów między kierunkami
4. **Maksymalny czas fazy** - faza nie może trwać dłużej niż 30 sekund

### Fazy świateł (TrafficPhase):
- **Faza 1**: north_road → east_road, south_road → west_road
- **Faza 2**: west_road → north_road, east_road → south_road  
- **Faza 3**: north_road → south_road, south_road → north_road (z pieszymi)
- **Faza 4**: west_road → east_road, east_road → west_road (z pieszymi)
- **Faza 5**: north_road → west_road, south_road → east_road
- **Faza 6**: west_road → south_road, east_road → north_road

## Punkty Końcowe API

### Symulacja
- `POST /api/simulation/execute` - wykonuje symulację na podstawie komend JSON
- `GET /api/simulation/health` - sprawdza status symulacji

### Pliki
- `POST /api/file/execute?inputFile=...&outputFile=...` - wykonuje symulację z pliku

### Monitorowanie
- `GET /api/monitoring/stats` - zwraca statystyki symulacji
- `GET /api/monitoring/health` - sprawdza status monitorowania

## Format JSON

### Wejście (SimulationRequest):
```json
{
  "commands": [
    {
      "type": "addVehicle",
      "vehicleId": "vehicle1",
      "startRoad": "south",
      "endRoad": "north"
    },
    {
      "type": "step"
    }
  ]
}
```

### Wyjście (SimulationResponse):
```json
{
  "stepStatuses": [
    {
      "leftVehicles": ["vehicle1", "vehicle2"]
    },
    {
      "leftVehicles": []
    }
  ]
}
```

## Uruchamianie

### Lokalnie
```bash
# Kompilacja
mvn clean compile

# Uruchomienie
mvn spring-boot:run -pl simulation-core
```

### Docker
```bash
# Budowanie obrazu
docker build -t traffic-simulation .

# Uruchomienie
docker run -p 8080:8080 traffic-simulation

# Lub z docker-compose
docker-compose up
```

## Testowanie

### Przykład użycia API:
```bash
# Test symulacji
curl -X POST http://localhost:8080/api/simulation/execute \
  -H "Content-Type: application/json" \
  -d @example-simulation.json

# Sprawdzenie statystyk
curl http://localhost:8080/api/monitoring/stats

# Sprawdzenie zdrowia
curl http://localhost:8080/api/simulation/health
```

### Przykład z plikiem:
```bash
# Wykonanie symulacji z pliku
curl -X POST "http://localhost:8080/api/file/execute?inputFile=example-simulation.json&outputFile=result.json"
```

## Funkcjonalności

### Podstawowe:
- Symulacja skrzyżowania z 4 drogami
- Inteligentny algorytm sterowania światłami
- Priorytetyzacja dróg z >5 autami
- Bezpieczeństwo - unikanie konfliktów
- API REST z formatem JSON
- Obsługa plików wejściowych/wyjściowych

### Dodatkowe:
- Symulacja z pieszymi którzy mają cztery lokalizacje: NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST
- Monitorowanie i statystyki
- Health checks
- Dockerfile i docker-compose
- CI/CD GitHub Actions

## Technologie

- **Java 21**
- **Spring Boot**
- **Maven**
- **Docker**
- **Jackson**
- **Spring Actuator**

## Struktura Projektu

```
AVSSystem/
├── simulation-algorithm/     # Algorytm i modele
├── simulation-core/         # Spring Boot API
├── frontend/               # React frontend (niezrealizowany)
├── Dockerfile              # Konfiguracja Docker
├── docker-compose.yml      # Docker Compose
├── pom.xml                 # Parent POM
└── README.md              # Dokumentacja
```
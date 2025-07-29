# Pipeline CI/CD - System AVS

## Przegląd

Ten projekt zawiera kompletny pipeline CI/CD z wykorzystaniem GitHub Actions, który automatycznie testuje, buduje i wdraża aplikację symulacji świateł drogowych.

## Workflow

### 1. **Pipeline CI/CD** (`.github/workflows/ci-cd.yml`)

**Wyzwalacz:** Push na `main`/`develop`, Pull Request na `main`, Release

**Zadania:**
- **Test** - Uruchamia testy jednostkowe
- **Build** - Buduje aplikację (po przejściu testów)
- **Security** - Skanuje zależności pod kątem podatności
- **Docker Build** - Buduje obraz Docker
- **Deploy Staging** - Wdraża na środowisko testowe (branch `develop`)
- **Deploy Production** - Wdraża na produkcję (release)
- **Notify** - Powiadamia o statusie

### 2. **Jakość Kodu** (`.github/workflows/code-quality.yml`)

**Wyzwalacz:** Push na `main`/`develop`, Pull Request na `main`

**Zadania:**
- **Analiza SonarQube** - Analiza jakości kodu
- **Pokrycie Kodu** - Raport pokrycia kodu
- **Przegląd Zależności** - Przegląd zależności

### 3. **Release** (`.github/workflows/release.yml`)

**Wyzwalacz:** Push tag `v*`

**Zadania:**
- **Utwórz Release** - Tworzy release na GitHub
- **Prześlij Pliki** - Dodaje pliki JAR i Docker

## Konfiguracja

### Wymagane Sekrety w GitHub:

```bash
# SonarCloud
SONAR_TOKEN=your-sonarcloud-token

# Docker Registry (opcjonalnie)
DOCKER_USERNAME=your-docker-username
DOCKER_PASSWORD=your-docker-password

# Deployment (opcjonalnie)
STAGING_HOST=your-staging-host
PRODUCTION_HOST=your-production-host
SSH_PRIVATE_KEY=your-ssh-key
```

### Środowiska w GitHub:

1. **staging** - Dla wdrożeń testowych
2. **production** - Dla wdrożeń produkcyjnych

## Metryki

### Pokrycie Kodu
- **JaCoCo** - Raporty pokrycia kodu
- **Codecov** - Integracja z Codecov.io
- **Minimalne pokrycie:** 80%

### Bezpieczeństwo
- **OWASP Dependency Check** - Skanowanie podatności
- **Przegląd Zależności** - Przegląd zależności
- **Fail on:** Moderate severity

### Bramki Jakości
- **SonarQube** - Analiza jakości kodu
- **Code smells** - Maksymalnie 10
- **Błędy** - Maksymalnie 5
- **Podatności** - Maksymalnie 2

## Wdrażanie

### Staging (Automatyczny)
```bash
# Wyzwalacz: Push na branch 'develop'
git push origin develop
```

### Produkcja (Ręczny)
```bash
# 1. Utwórz tag
git tag v1.0.0

# 2. Push tag
git push origin v1.0.0

# 3. Utwórz release na GitHub
```

## Monitorowanie

### Kontrole Zdrowia
- **Endpoint:** `/api/simulation/health`
- **Docker:** Health check co 30s
- **Kubernetes:** Liveness/Readiness probes

### Metryki
- **Spring Boot Actuator** - `/actuator/metrics`
- **Własne metryki** - Liczba symulacji, pojazdów
- **Prometheus** - Integracja z monitoringiem

## Lokalne Testowanie

### Testowanie Pipeline
```bash
# Uruchom testy
mvn clean test

# Sprawdź pokrycie kodu
mvn jacoco:report

# Analiza SonarQube
mvn sonar:sonar
```

### Budowanie Docker
```bash
# Buduj obraz
docker build -t avs-system .

# Uruchom kontener
docker run -p 8080:8080 avs-system
```

## Lista Kontrolna przed Wdrożeniem

- [ ] Wszystkie testy przechodzą
- [ ] Pokrycie kodu > 80%
- [ ] Brak podatności bezpieczeństwa
- [ ] Bramki jakości SonarQube przeszły
- [ ] Budowanie Docker udane
- [ ] Dokumentacja zaktualizowana

## Rozwiązywanie Problemów

### Problem: Testy nie przechodzą
```bash
# Sprawdź logi
mvn test -X

# Uruchom konkretny test
mvn test -Dtest=DetailedSimulationTest
```

### Problem: Budowanie Docker nie udaje się
```bash
# Sprawdź Dockerfile
docker build --no-cache -t avs-system .

# Sprawdź logi
docker-compose logs
```

### Problem: Wdrożenie nie udaje się
```bash
# Sprawdź zmienne środowiskowe
echo $STAGING_HOST
echo $PRODUCTION_HOST

# Sprawdź połączenie SSH
ssh -i ~/.ssh/id_rsa user@host
```

## Wsparcie

- **Issues:** GitHub Issues
- **Discussions:** GitHub Discussions
- **Documentation:** Wiki

---

**System AVS - Symulacja Inteligentnych Świateł Drogowych** 
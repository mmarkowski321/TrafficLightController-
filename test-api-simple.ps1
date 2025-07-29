Write-Host "=== TEST API SYMULACJI ==="

try {
    $health = Invoke-RestMethod -Uri "http://localhost:8080/api/simulation/health" -Method GET
    Write-Host "Aplikacja działa: $health"
} catch {
    Write-Host "Aplikacja nie działa na porcie 8080"
    Write-Host "Uruchom aplikację: cd simulation-core; mvn spring-boot:run"
    exit 1
}

$testData = @{
    commands = @(
        @{
            type = "addVehicle"
            vehicleId = "vehicle1"
            startRoad = "south"
            endRoad = "north"
        },
        @{
            type = "addVehicle"
            vehicleId = "vehicle2"
            startRoad = "north"
            endRoad = "south"
        },
        @{
            type = "step"
        },
        @{
            type = "step"
        },
        @{
            type = "addVehicle"
            vehicleId = "vehicle3"
            startRoad = "west"
            endRoad = "south"
        },
        @{
            type = "addVehicle"
            vehicleId = "vehicle4"
            startRoad = "west"
            endRoad = "south"
        },
        @{
            type = "step"
        },
        @{
            type = "step"
        }
    )
}

$jsonBody = $testData | ConvertTo-Json -Depth 10

Write-Host "`nWysyłanie żądania:"
Write-Host $jsonBody

try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/simulation/execute" -Method POST -Body $jsonBody -ContentType "application/json"
    
    Write-Host "`nOdpowiedź:"
    $response | ConvertTo-Json -Depth 10
    
    Write-Host "`nTest przeszedł pomyślnie!"
} catch {
    Write-Host "Błąd: $($_.Exception.Message)"
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Szczegóły błędu: $responseBody"
    }
} 
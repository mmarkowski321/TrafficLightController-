$body = Get-Content "test-simulation.json" -Raw
Write-Host "Testing API with request:"
Write-Host $body
Write-Host "`nResponse:"
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/api/simulation/execute" -Method POST -Body $body -ContentType "application/json"
    $response | ConvertTo-Json -Depth 10
} catch {
    Write-Host "Error: $($_.Exception.Message)"
} 
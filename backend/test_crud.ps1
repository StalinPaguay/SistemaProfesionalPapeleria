$token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2wiOiJBRE1JTklTVFJBRE9SIiwic3ViIjoiYWRtaW5AcGFwZXJkcy5jb20iLCJpYXQiOjE3ODA5MjYxMDMsImV4cCI6MTc4MTAxMjUwM30.0jyFuLXtS-7ZqFokRCHR4CX4opzVhTE2qQb6YNQ9TRw"
$headers = @{ "Authorization" = "Bearer $token"; "Content-Type" = "application/json" }

Write-Host "--- TEST: CREATE CATEGORIA ---"
$createPayload = '{"nombre":"Categoria Test CRUD","descripcion":"Desc Test"}'
$createResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/categorias" -Method Post -Headers $headers -Body $createPayload
Write-Host "Created:" ($createResponse | ConvertTo-Json -Depth 2 -Compress)
$catId = $createResponse.id

Write-Host "--- TEST: READ CATEGORIAS ---"
$readResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/categorias" -Method Get -Headers $headers
Write-Host "Total Categories Found:" $readResponse.Count

Write-Host "--- TEST: UPDATE CATEGORIA ---"
$updatePayload = '{"nombre":"Categoria Test CRUD Updated","descripcion":"Desc Test Updated"}'
$updateResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/categorias/$catId" -Method Put -Headers $headers -Body $updatePayload
Write-Host "Updated:" ($updateResponse | ConvertTo-Json -Depth 2 -Compress)

Write-Host "--- TEST: DELETE CATEGORIA ---"
Invoke-RestMethod -Uri "http://localhost:8080/api/categorias/$catId" -Method Delete -Headers $headers
Write-Host "Deleted Successfully."

Write-Host "ALL CRUD OPERATIONS COMPLETED SUCCESSFULLY!"

# Ejemplos SOAP (Demostración)

A continuación, se presentan los XMLs para poder probar el módulo SOAP a través de Postman o SOAP UI.

### 1. WSDL URL
`http://localhost:8080/ws/productos.wsdl`

---

### 2. Obtener Productos (`obtenerProductosRequest`)

**Endpoint:** `POST http://localhost:8080/ws`
**Headers:** `Content-Type: text/xml`

**SOAP Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:prod="http://example.org/soap/productos">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">secret</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <prod:obtenerProductosRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```

**SOAP Response (Ejemplo):**
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:obtenerProductosResponse xmlns:ns2="http://example.org/soap/productos">
         <ns2:productos>
            <ns2:id>1</ns2:id>
            <ns2:nombre>Cuaderno A4</ns2:nombre>
            <ns2:categoria>Cuadernos</ns2:categoria>
            <ns2:precio>5.50</ns2:precio>
            <ns2:stock>100</ns2:stock>
            <ns2:marca>Norma</ns2:marca>
            <ns2:descripcion>Cuaderno cuadriculado de 100 hojas</ns2:descripcion>
            <ns2:imagenUrl>https://images.unsplash.com/photo-1531346878377-a5be20888e57?w=500&amp;auto=format&amp;fit=crop&amp;q=60&amp;ixlib=rb-4.0.3</ns2:imagenUrl>
         </ns2:productos>
      </ns2:obtenerProductosResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

---

### 3. Importar Productos Masivos (`importarProductosMasivosRequest`)

**Endpoint:** `POST http://localhost:8080/ws`
**Headers:** `Content-Type: text/xml`

**SOAP Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:prod="http://example.org/soap/productos">
   <soapenv:Header>
      <wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>admin</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">secret</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <prod:importarProductosMasivosRequest>
         <prod:productos>
            <prod:nombre>Lápiz HB 2</prod:nombre>
            <prod:categoria>Útiles de Escritorio</prod:categoria>
            <prod:precio>0.50</prod:precio>
            <prod:stock>500</prod:stock>
            <prod:marca>Faber-Castell</prod:marca>
            <prod:descripcion>Lápiz estándar para dibujo y escritura</prod:descripcion>
            <prod:imagenUrl>https://images.unsplash.com/photo-1583485088034-697b5a69fdd9?w=500&amp;auto=format&amp;fit=crop&amp;q=60&amp;ixlib=rb-4.0.3</prod:imagenUrl>
         </prod:productos>
         <prod:productos>
            <prod:nombre>Borrador Blanco</prod:nombre>
            <prod:categoria>Útiles de Escritorio</prod:categoria>
            <prod:precio>0.30</prod:precio>
            <prod:stock>300</prod:stock>
            <prod:marca>Pelikan</prod:marca>
            <prod:descripcion>Borrador de miga</prod:descripcion>
            <prod:imagenUrl>https://images.unsplash.com/photo-1580210287515-37de0a3594ac?w=500&amp;auto=format&amp;fit=crop&amp;q=60&amp;ixlib=rb-4.0.3</prod:imagenUrl>
         </prod:productos>
      </prod:importarProductosMasivosRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

**SOAP Response (Ejemplo):**
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:importarProductosMasivosResponse xmlns:ns2="http://example.org/soap/productos">
         <ns2:mensaje>Importación masiva exitosa.</ns2:mensaje>
         <ns2:cantidadImportada>2</ns2:cantidadImportada>
      </ns2:importarProductosMasivosResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

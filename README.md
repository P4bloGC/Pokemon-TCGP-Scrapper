
# tcgp-scrapper

**tcgp-scrapper** es un scrapper diseñado en Java/Spring Boot para extraer información de las cartas del juego móvil Pokémon Trading Card Game Pocket (TCGP).  
El contenido es scrapeado desde el sitio web Pocket Limitless TCG (https://pocket.limitlesstcg.com).

---

## Requisitos

- **Java**: Versión 17 o superior.
- **Maven**: Para la gestión de dependencias.
- **Spring Boot**: Versión 3.2.3.

---

## Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/P4bloGC/Pokemon-TCGP-Scrapper.git
   cd tcgp-scrapper
   ```

2. **Construir el proyecto**:
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**:
   ```bash
   mvn spring-boot:run
   ```

4. **Acceder a Swagger**:
   Abre tu navegador y accede a [http://localhost:8080/tgcp-scraper/swagger-ui/index.html] para visualizar y probar los endpoints.

---

## Endpoints principales

### **Scraping de cartas**
- **Endpoint**: `/api/scraper/cards`
- **Método**: `GET`
- **Descripción**: Obtiene todas las cartas disponibles en las URLs configuradas.
- **Respuesta**: Devuelve una lista de de cartas en formato JSON.

---

## Configuración adicional

### **URLs base**
Las URLs base que el scrapper utiliza están configuradas en `PocketLimitlessScraperService`:

```java
private static final List<String> BASE_URLS = List.of(
    "https://pocket.limitlesstcg.com/cards/A1/",
    "https://pocket.limitlesstcg.com/cards/P-A/"
);
```

Puedes agregar o modificar estas URLs según sea necesario para scrapeos adicionales a futuro.

---

## Ejemplo de respuesta

```json
[
  {
    "cardNumber": "188",
    "cardType": "Pokémon",
    "set": "Genetic Apex (A1)",
    "pack": "Mewtwo pack",
    "name": "Pidgeot",
    "stage": 3,
    "evolvesFrom": "Pidgeotto",
    "type": "Colorless",
    "hp": "130",
    "retreatCost": "1",
    "weakness": "Lightning",
    "artist": "Scav",
    "imageUrl": "https://limitlesstcg.nyc3.digitaloceanspaces.com/pocket/A1/A1_188_EN.webp",
    "abilites": [
      {
        "name": "Drive Off",
        "description": "Once during your turn, you may switch out your opponent's Active Pokémon to the Bench. (Your opponent chooses the new Active Pokémon.)"
      }
    ],
    "attacks": [
      {
        "cost": "CC",
        "name": "Wing Attack",
        "effect": "",
        "power": "70",
        "variableDamage": null
      }
    ],
    "effect": null,
    "rarity": "◊◊◊"
  }
]
```

---

## Contribución

1. Haz un fork del repositorio.
2. Crea una rama para tu funcionalidad o corrección:
   ```bash
   git checkout -b mi-nueva-funcionalidad
   ```
3. Envía un pull request con tus cambios.

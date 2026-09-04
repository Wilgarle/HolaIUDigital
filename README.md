# HolaIUDigital - Sistema de Mensajería Básico para Android

Aplicación móvil nativa para Android desarrollada en **Java**, diseñada bajo arquitectura modular de dos actividades (`MainActivity` y `Activity2`) para la transferencia explícita de datos mediante `Intent` y el procesamiento síncrono de respuestas utilizando la API moderna `ActivityResultLauncher`.

---

##  Características del Proyecto

* **Patrón de Navegación Explícita**: Comunicación directa e inequívoca entre componentes de aplicación a través de `Intent` y la estructura de datos `Bundle` (`putExtra` / `getStringExtra`).
* **Manejo Moderno de Resultados (`ActivityResultLauncher`)**: Reemplazo del estándar deprecado `startActivityForResult()`, garantizando la gestión desacoplada del ciclo de vida y previniendo fugas de memoria o inconsistencias tras cambios de configuración (rotación de pantalla).
* **Diseño Responsivo e Interfaz Adaptativa**: Construcción de interfaces gráficas mediante `LinearLayout` con distribución de pesos (`layout_weight="1"`) y anclajes proporcionales (`0dp`), adaptándose fluidamente en orientaciones Vertical (*Portrait*) u Horizontal (*Landscape*).
* **Paleta de Colores Limpia y Profesional**: Implementación de recursos estilizados basados en tonos blanco, azul claro y azul oscuro (`colors.xml`).
* **Buenas Prácticas y Accesibilidad**: Desacoplamiento total de recursos de cadena (`strings.xml`), eliminación de texto e imágenes hardcodeados y soporte de atributos para lectores de pantalla (`contentDescription` e `importantForAutofill`).

---

##  Arquitectura del Sistema

```
app/src/main/
├── java/com/example/holaiudigital/
│   ├── MainActivity.java      # Pantalla principal: captura de mensaje y recepción de estado
│   └── Activity2.java         # Pantalla secundaria: despliegue de mensaje y emisión de respuesta
└── res/
    ├── drawable/
    │   ├── ic_logo_main.png    # Logotipo representativo de la pantalla principal
    │   └── ic_logo_activity2.png # Logotipo representativo de la pantalla secundaria
    ├── layout/
    │   ├── activity_main.xml  # Interfaz gráfica de la actividad principal
    │   └── activity_2.xml     # Interfaz gráfica de la actividad secundaria
    └── values/
        ├── colors.xml         # Definición centralizada de colores corporativos
        ├── strings.xml        # Cadenas de texto internacionalizables
        └── themes.xml         # Estilo estético global de la aplicación
```

---

##  Flujo de Funcionamiento

1. **Pantalla Principal (`MainActivity`)**:
   * El usuario ingresa un mensaje en el componente `EditText`.
   * Al presionar el botón **"Enviar"**, se valida que la cadena no esté vacía.
   * Se instancia un `Intent` explícito con la clave `"MENSAJE_ENVIADO"` y se lanza la navegación mediante `activityResultLauncher.launch(intent)`.
   * El componente `TextView` inferior muestra dinámicamente el estado del envío.

2. **Pantalla Secundaria (`Activity2`)**:
   * Al iniciar, la actividad recupera el mensaje adjunto desde los datos `extra` del `Intent` y lo despliega centrado en pantalla.
   * Cuenta con dos opciones de respuesta organizadas horizontalmente: **"Recibido"** y **"Cancelado"**.
   * Al seleccionar cualquiera de los dos botones, la actividad adjunta la respuesta en un `Intent` de retorno con la clave `"RESPUESTA"`, establece el código `setResult(RESULT_OK, intentRespuesta)` y finaliza su ciclo de vida con `finish()`.

3. **Retorno de Respuesta**:
   * `MainActivity` captura el resultado en su *callback* `onActivityResult`, extrayendo la clave `"RESPUESTA"` y actualizando el `TextView` de estado.

---

##  Paleta de Colores (`colors.xml`)

| Nombre del Color | Código Hexadecimal | Muestra | Descripción |
| :--- | :--- | :--- | :--- |
| `white` | `#FFFFFF` | `#FFFFFF` | Fondo principal y texto sobre botones |
| `light_blue` | `#F0F8FF` | `#F0F8FF` | Fondo secundario |
| `blue_primary` | `#0056B3` | `#0056B3` | Color primario para botones y resaltados |
| `blue_secondary` | `#6699CC` | `#6699CC` | Color secundario para botones de acción |
| `dark_blue` | `#003366` | `#003366` | Color oscuro para textos e indicadores |
| `gray_hint` | `#757575` | `#757575` | Color para sugerencias de entrada (hints) |

---

## 🛠️ Tecnologías y Requisitos

* **Lenguaje**: Java (JDK 11 / 17)
* **SDK Mínimo Requerido**: API level 24 (Android 7.0 Nougat)
* **SDK Objetivo**: API level 35 (Android 15)
* **Entorno de Desarrollo**: Android Studio Ladybug / Jellyfish (2024.x+)
* **Sistema de Construcción**: Gradle 8.x con Kotlin DSL (`build.gradle.kts`)

---

##  Instalación y Compilación

1. Clonar el repositorio desde GitHub:
   ```bash
   git clone https://github.com/wilgarle/HolaIUDigital.git
   cd HolaIUDigital
   ```
2. Abrir el proyecto en **Android Studio**.
3. Permitir que Gradle sincronice las dependencias del proyecto (`Gradle Sync`).
4. Compilar la aplicación ejecutando:
   ```bash
   ./gradlew assembleDebug
   ```
5. Ejecutar en un emulador Android (AVD) o dispositivo físico conectado mediante depuración USB.

---

##  Licencia y Créditos

Desarrollado como proyecto académico nativo en **Java** para **IUDigital de Antioquia**.
Autor: **wilgarle** (`william.garcial@est.iudigital.edu.co`).

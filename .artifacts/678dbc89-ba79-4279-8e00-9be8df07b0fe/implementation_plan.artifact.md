# Plan de Mejoras de Interfaz y Refactorización

Implementar las mejoras de interfaz y características solicitadas por el usuario para EcoAlert.

## Revisión Requerida del Usuario

> [!IMPORTANT]
> - Eliminación del botón de estadísticas/análisis de la barra superior.
> - Adición de una pestaña/formulario de registro dentro de la vista de perfil/cuenta de usuario.
> - Fusión del mensaje de calidad del aire y la recomendación dentro de la tarjeta principal de Calidad del Aire (Hero AQI) y eliminación de la tarjeta de recomendación inferior separada.
> - Cambio de nombre de las pestañas superiores a "Reporte" y "Detalle contaminantes".
> - Rediseño de las tarjetas de métricas ambientales y adición de la tarjeta de presión atmosférica.

## Cambios Propuestos

### [EcoAlertScreen.kt](file:///C:/Users/patri/AndroidStudioProjects/EcoAlert/app/src/main/java/com/example/pruebasubicacion/presentation/view/EcoAlertScreen.kt)

#### [MODIFY] [EcoAlertScreen.kt](file:///C:/Users/patri/AndroidStudioProjects/EcoAlert/app/src/main/java/com/example/pruebasubicacion/presentation/view/EcoAlertScreen.kt)
- **TopDashboardBar**: Eliminar el botón de icono de análisis.
- **ProfileAccountView**: Agregar un selector/pestaña entre "Iniciar Sesión" y "Registrarse" con campos completos de registro (Nombre, Apellido, Correo, Contraseña, Confirmar Contraseña, Peso, Sexo).
- **HeroAQIArcMeter**: Mejorar el estilo visual (degradado, bordes y elevación) y fusionar el mensaje de recomendación de salud/actividad directamente dentro de la tarjeta.
- **ProfileSwitchSegmented**: Renombrar las pestañas a "Reporte" (Tab 0) y "Detalle contaminantes" (Tab 1). Eliminar `ProfileRecommendationBox` ya que su contenido ahora está integrado en la tarjeta Hero AQI.
- **EnvironmentalMetricsGrid & MetricTileAdaptive**: Rediseñar las tarjetas de métricas con superficies modernas, iconos y agregar la tarjeta de Presión Atmosférica.

## Plan de Verificación

### Pruebas Automatizadas
- Ejecutar compilación de Gradle para asegurar que el proyecto compila correctamente.

### Verificación Manual
- Desplegar la aplicación en el dispositivo/emulador usando la herramienta `deploy` e inspeccionar la interfaz.

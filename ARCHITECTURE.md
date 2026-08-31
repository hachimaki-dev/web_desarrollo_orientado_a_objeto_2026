# Arquitectura — Plataforma Docente

Este documento explica cómo funciona la plataforma, qué archivos tocar y cómo crear nuevo contenido. Léelo completo antes de hacer cambios.

## Stack técnico

- **HTML5** — estructura de cada contenido
- **Vue 3 via CDN** — reactividad e interactividad (sin build tools)
- **Highlight.js via CDN** — syntax highlighting para Java
- **CSS vanilla** — design system con custom properties

Cada archivo `.html` es **autocontenido**: importa CSS y JS desde `shared/` vía rutas relativas. Se abre directo en el navegador sin servidor.

## Archivos del sistema (shared/)

### `fonts.css`
Importa Inter y JetBrains Mono desde Google Fonts. Se importa antes de `styles.css`.

### `styles.css`
Design system completo. Contiene:
- **Tokens** (`:root`): colores, espaciado, radios, tipografía
- **Layout de slides**: `.app`, `.topbar`, `.slide-area`, `.slide`, `.controls-bar`
- **Componentes**: `.card`, `.code-block`, `.rule`, `.callout`, `.tag`, `.grid`
- **Actividades**: `.question-block`, `.option-list`, `.matching-grid`, `.wordsearch-grid`, `.crossword-grid`, `.code-fill`
- **Feedback**: `.correct`, `.incorrect`, `.feedback`, `.hint-btn`, `.hint-text`
- **Portal**: `.portal-container`, `.content-card`, `.unit-section`
- **DUA tags**: `.tag-base`, `.tag-inter`, `.tag-avanzado`
- **Responsive** (≤760px) y **Print** (oculta controles)

### `slide-engine.js`
Motor de presentaciones. API:

```javascript
SlideEngine.mount('#app', {
  title: 'Nombre de la presentación',
  totalSlides: 25
});
```

La app Vue espera esta estructura HTML:
```html
<div id="app" class="app">
  <header class="topbar">
    <div class="topbar-brand"><span class="alien">👽</span> {{ title }}</div>
    <div class="topbar-counter">{{ current + 1 }} / {{ total }}</div>
    <div class="progress-bar" :style="{width: progressWidth}"></div>
  </header>
  <main class="slide-area">
    <section v-if="current===0" data-slide class="slide">...</section>
    <section v-else-if="current===1" data-slide class="slide">...</section>
    ...
  </main>
  <footer class="controls-bar">
    <button class="btn" @click="prev">← Anterior</button>
    <div class="dots">
      <span v-for="i in total" :key="i" class="dot" :class="{active: current===i-1}" @click="goTo(i-1)"></span>
    </div>
    <button class="btn" @click="next">Siguiente →</button>
  </footer>
</div>
```

Navegación automática por teclado: ← → Home End PageUp PageDown.
Highlight.js se aplica automáticamente al cambiar de slide.

### `activity-engine.js`
Motor de actividades. Se registra después de crear la app Vue:

```javascript
const { createApp } = Vue;
const app = createApp({ ... });
ActivityEngine.register(app);  // ← registra todos los componentes
app.mount('#app');
```

**Componentes disponibles:**

| Componente | Uso | Props principales |
|------------|-----|-------------------|
| `<quiz-question>` | Alternativas o V/F | `question`, `options`, `explanation`, `hint`, `level` |
| `<multi-select>` | Selección múltiple | `question`, `options`, `explanation`, `hint`, `level` |
| `<code-error>` | Encontrar error en código | `instruction`, `codeLines`, `explanation`, `hint`, `level` |
| `<code-fill>` | Completar código faltante | `instruction`, `segments`, `explanation`, `hint`, `level` |
| `<matching-pairs>` | Términos pareados | `instruction`, `pairs`, `level` |
| `<word-search>` | Sopa de letras | `title`, `gridSize`, `words`, `level` |
| `<crossword-puzzle>` | Crucigrama | `title`, `gridData`, `acrossClues`, `downClues`, `level` |
| `<order-steps>` | Ordenar pasos de código | `instruction`, `steps`, `explanation`, `level` |

Prop `level`: `'base'` (🟢), `'inter'` (🔵), `'avanzado'` (🟣).
Todos tienen retroalimentación inmediata y botón de reintentar.

## Cómo crear un nuevo contenido

### Paso 1 — Determinar tipo y nombre
Decidir si es `PPT`, `Guia` o `Ejercicios`. Nombrar siguiendo el patrón `[unidad].[subunidad]_[tipo]_[nombre].html`.

### Paso 2 — Crear la carpeta de unidad (si no existe)
Dentro de `contenidos/`, crear la carpeta de la unidad: `1.4_interfaces/`, `2.1_colecciones/`, etc.

### Paso 3 — Crear el archivo HTML
Usar como base la estructura de un archivo existente del mismo tipo:
- Para PPT → copiar estructura de `1.3.3_PPT_Polimorfismo.html`
- Para Guía → copiar estructura de `1.3.4_Guia_Actividad_Herencia_y_Polimorfismo.html`
- Para Ejercicios → copiar estructura de `1.3.5_Ejercicios_Generales.html`

### Paso 4 — Imports requeridos
Todo archivo HTML debe incluir en su `<head>`:

```html
<link rel="stylesheet" href="../../shared/fonts.css">
<link rel="stylesheet" href="../../shared/styles.css">
<script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/base16/catppuccin-mocha.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/java.min.js"></script>
```

Y antes de cerrar `</body>`:
```html
<script src="../../shared/slide-engine.js"></script>
<script src="../../shared/activity-engine.js"></script>
```

### Paso 5 — Seguir la guía de contenido
Leer `CONTENT_GUIDE.md` para tono, estructura de la clase y reglas DUA.

### Paso 6 — Agregar al portal
Agregar un enlace en `index.html` dentro de la sección de unidad correspondiente.

### Paso 7 — Verificar
Abrir el archivo en el navegador. Verificar:
- Navegación funciona (teclado + botones)
- Syntax highlighting se ve correcto
- Actividades dan retroalimentación
- Responsive funciona (reducir ventana)
- Los 3 niveles DUA están representados

## Convenciones de código en ejemplos Java

- Usar el código **real** de los repos del curso cuando corresponda
- Los ejemplos genéricos deben usar dominios simples (Animal, Vehículo, Figura)
- Indentar con 4 espacios
- Incluir siempre el contexto mínimo necesario (imports, clase, método)
- Usar `<pre class="code-block"><code class="language-java">` para bloques de código

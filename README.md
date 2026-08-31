# Plataforma Docente — Java OOP · Profe Carlitos 👽

Plataforma web de material pedagógico para la asignatura **Desarrollo Orientado a Objetos** (2026, 2° semestre).

## Docente
- **Carlos Orellana** (Profe Carlitos · HachiMaki-dev)
- GitHub: [hachimaki-dev](https://github.com/hachimaki-dev)

## Secciones y repositorios

| Sección | Repositorio | Temática del proyecto |
|---------|-------------|-----------------------|
| 002D | [002D_desarrollo_orientado_a_objeto_2026](https://github.com/hachimaki-dev/002D_desarrollo_orientado_a_objeto_2026) | Minecraft: `Character` → `Zombie`, `Dragon`, `Creeper`, `Enderman` |
| 004D | [004D_Desarrollo_orientado_a_Objeto_2026](https://github.com/hachimaki-dev/004D_Desarrollo_orientado_a_Objeto_2026) | Programadores: `Personaje` → `Hacker`, `Programador`, `GameDev` |

## Cómo usar

1. Abrir cualquier archivo `.html` directamente en el navegador (no requiere servidor).
2. Navegar con las flechas del teclado (← →), Page Up/Down, Home/End o los botones en pantalla.
3. Las actividades interactivas dan retroalimentación inmediata.

## Para agentes IA

Antes de crear o modificar contenido, **leer en este orden**:
1. Este `README.md` — contexto general
2. `ARCHITECTURE.md` — cómo funciona el sistema técnico, cómo crear un nuevo contenido
3. `CONTENT_GUIDE.md` — tono, estilo, DUA, reglas anti-slop

## Estructura del proyecto

```
plataforma-docente/
├── README.md                   ← Este archivo
├── ARCHITECTURE.md             ← Guía técnica
├── CONTENT_GUIDE.md            ← Guía de contenido
├── index.html                  ← Portal de navegación
├── shared/
│   ├── fonts.css               ← Google Fonts (Inter, JetBrains Mono)
│   ├── styles.css              ← Design system (tokens + componentes)
│   ├── slide-engine.js         ← Motor de presentaciones (Vue 3)
│   └── activity-engine.js      ← Motor de actividades interactivas
└── contenidos/
    └── 1.3_herencia_y_polimorfismo/
        ├── 1.3.3_PPT_Polimorfismo.html
        ├── 1.3.4_Guia_Actividad_Herencia_y_Polimorfismo.html
        ├── 1.3.5_Ejercicios_Generales.html
        └── 1.3.6_UML_Instagram_CheatSheet.html
```

## Nomenclatura de contenidos

Cada archivo sigue el patrón: `[unidad].[subunidad]_[tipo]_[nombre].html`

- Tipos: `PPT` (presentación), `Guia` (guía de actividad), `Ejercicios` (batería de ejercicios)

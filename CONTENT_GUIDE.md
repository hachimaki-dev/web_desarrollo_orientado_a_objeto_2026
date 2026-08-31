# Guía de Contenido — Plataforma Docente

## Contexto pedagógico

Los estudiantes vienen de Python funcional básico. Java y la POO son nuevos para ellos. Cada concepto debe construirse sobre lo anterior, usando lenguaje preciso pero accesible.

El rango de estudiantes incluye desde quienes aprenden rápido hasta estudiantes con Necesidades Educativas Especiales (NEE). Todo contenido debe funcionar para ambos extremos.

## Estructura de cada clase

Cada contenido (especialmente las PPT) sigue esta secuencia:

1. **Concepto genérico** — Explicar el concepto usando ejemplos de dominio neutro (Animal, Vehículo, Figura). Sin referencia a los proyectos del curso.

2. **Aterrizado a los proyectos** — Mostrar cómo se aplica al código real del curso:
   - Sección 002D: Minecraft (`Character` → `Zombie`, `Dragon`, `Creeper`, `Enderman`)
   - Sección 004D: Programadores (`Personaje` → `Hacker`, `Programador`, `GameDev`)

3. **Verificación de comprensión** — Actividades para que el estudiante confirme que entendió, con retroalimentación inmediata.

## Tono y estilo

### Reglas fundamentales

1. **Cada oración debe aportar comprensión o instrucción concreta.** Si una oración se puede eliminar sin perder información, eliminarla.

2. **No usar frases decorativas.** Ejemplos prohibidos:
   - "¡Prepárate para un emocionante viaje..."
   - "¡Excelente! Ya estás listo para..."
   - "Como hemos visto en este fascinante..."
   - "¡Felicidades por llegar hasta aquí!"

3. **Tono de docente experto.** Profesional, directo, que respeta la inteligencia de los estudiantes. No condescendiente, no infantilizado, no artificialmente entusiasta.

4. **Explicar con precisión técnica accesible.** Usar los términos correctos pero siempre dar el significado cuando se introducen por primera vez.

5. **Preguntas que provocan pensamiento.** En vez de "¿No es genial?", usar "¿Qué pasa si el método no existe en la clase hija?" o "¿Por qué esto es preferible a la alternativa?".

### Ejemplos de tono correcto

```
✓ "Polimorfismo significa que un mismo mensaje puede producir comportamientos diferentes según el objeto que lo recibe."

✓ "La clase hija puede redefinir un método heredado. En Java, esto se marca con @Override."

✓ "Observa qué método se ejecuta cuando la variable es de tipo Character pero el objeto es Zombie."
```

### Ejemplos de tono incorrecto

```
✗ "¡El increíble poder del polimorfismo transformará tu forma de programar!"

✗ "¡Maravilloso! Ahora que entiendes herencia, vamos a subir de nivel."

✗ "En este apasionante capítulo descubriremos los secretos del @Override."
```

## Diseño Universal de Aprendizaje (DUA)

### Principio 1 — Múltiples formas de representación

Cada concepto se presenta de al menos dos formas:
- **Texto** — explicación escrita clara
- **Código** — ejemplo funcional en Java
- **Esquema visual** — diagrama de jerarquía, tabla comparativa, o flujo

### Principio 2 — Múltiples formas de acción y expresión

Variedad de actividades para que diferentes estudiantes demuestren comprensión:
- Seleccionar respuestas (alternativas, V/F)
- Identificar errores en código
- Completar código faltante
- Conectar conceptos (términos pareados)
- Ordenar secuencias lógicas

### Principio 3 — Múltiples formas de compromiso

Tres niveles de dificultad explícitos:
- **🟢 Base** — Reconocer, identificar, recordar. Para que todos los estudiantes puedan participar.
- **🔵 Intermedio** — Aplicar, analizar, conectar. El nivel esperado para la mayoría.
- **🟣 Avanzado** — Evaluar, crear, resolver problemas nuevos. Para quienes necesitan un desafío adicional.

Cada actividad indica su nivel. Las pistas están disponibles para quienes las necesiten, sin penalización.

## Paleta de colores

| Uso | Color | Hex |
|-----|-------|-----|
| Fondo principal | Blanco | `#FFFFFF` |
| Texto principal | Negro/gris oscuro | `#1A1A2E` |
| Texto secundario | Gris | `#4A4A5A` |
| Lavanda (acento) | Pastel | `#E8E0F0` |
| Menta (acento) | Pastel | `#D4EDDA` |
| Durazno (acento) | Pastel | `#FCE4CC` |
| Celeste (acento) | Pastel | `#D6EAF8` |
| Correcto | Verde suave | `#D4EDDA` |
| Incorrecto | Rosa suave | `#F8D7DA` |

## Iconos

Solo usar dos iconos para representar al profe:
- 👽 Alien
- 🛸 Nave espacial

No usar otros emojis decorativos. No usar emojis en los contenidos técnicos.

## Código Java en ejemplos

- Indentar con 4 espacios (no tabs)
- Incluir solo el código necesario para el concepto
- Resaltar las líneas clave con la clase `.hl`
- Comentarios solo cuando aportan comprensión
- Usar el código real de los repos cuando se está aterrizando al proyecto

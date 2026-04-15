# Revisión técnica del proyecto `GestionMongoDB`

## 1) Visión general

El proyecto está bien orientado a **práctica docente de MongoDB con Java** y cubre tres enfoques:

- Operaciones directas con `Document` (`operaciones`): útil para entender consultas y operadores de MongoDB.
- Persistencia con POJOs embebidos (`pojo`).
- Variantes con normalización/collation (`pojonormalizado`, `pojocollations`).

La estructura por paquetes separa razonablemente **modelo**, **DAO**, **acceso a Mongo** y **lógica de consola**.

---

## 2) Comentarios explicativos sobre el código

### 2.1 `operaciones.Main`: menú grande y acoplamiento con infraestructura

- El `main` concentra demasiadas responsabilidades: UI por consola, validación de entradas y orquestación de casos de uso.
- Hay una oportunidad de legibilidad extrayendo cada opción a métodos privados (`handleCrearColeccion`, `handleInsertarAlumno`, etc.).
- Se detecta un texto arrastrado por refactor (`"pojo.modelo.Curso:"`) en mensajes al usuario.

**Impacto**: el mantenimiento se vuelve más costoso cuando el menú crece.

### 2.2 `operaciones.MongoManager`: buen catálogo de operaciones, pero mezcla de capas

- La clase implementa correctamente muchos operadores (`$gte`, `$all`, `$size`, `addToSet`, `pull`, `max`, proyecciones con `slice`, etc.), lo cual es excelente para aprendizaje.
- A la vez, combina:
  1. acceso a datos,
  2. formateo de salida por consola,
  3. lógica de importación/exportación.

Esto provoca métodos extensos y poco reutilizables en otros contextos (por ejemplo, API REST).

### 2.3 `pojo` / `pojonormalizado` / `pojocollations`: patrón DAO claro

- Los `DAO` son sencillos y comprensibles.
- En varios métodos (`buscarPorNombre`, `buscarPorCodigo`) se usa `Filters.regex(..., "i")` sin anclar el patrón.

**Comentario funcional**: con regex no anclada, buscar `"ana"` puede devolver `"Susana"`. Si ese comportamiento no es deseado, conviene usar igualdad exacta o regex anclada (`^...$`).

### 2.4 `MongoManager` (POJO): configuración y seguridad

- Correcto uso de `PojoCodecProvider` para mapear clases Java.
- Sin embargo, hay credenciales en código fuente (usuario/contraseña dentro de `conectar`).

**Riesgo**: exposición accidental de secretos y dificultad para cambiar entornos (local, pruebas, producción).

---

## 3) Propuestas de mejora (priorizadas)

## Prioridad alta

1. **Externalizar credenciales y URI**
   - Usar variables de entorno (`MONGODB_URI`, `MONGODB_DB`).
   - Mantener valores por defecto seguros solo para entorno local.

2. **Separar responsabilidades**
   - Crear una capa `service` entre `Main` y `MongoManager/DAO`.
   - Dejar `Main` solo para interacción de consola.

3. **Manejo de errores uniforme**
   - Evitar `printStackTrace()` en importación.
   - Devolver resultados de operación y centralizar mensajes de error.

## Prioridad media

4. **Normalizar búsqueda por texto**
   - Si se quiere coincidencia exacta (ignorando mayúsculas/minúsculas), usar regex anclada y escapada.
   - Ejemplo orientativo:

```java
Pattern pattern = Pattern.compile("^" + Pattern.quote(nombre) + "$", Pattern.CASE_INSENSITIVE);
collection.find(Filters.regex("nombre", pattern)).first();
```

5. **Evitar duplicación entre paquetes `pojo`, `pojonormalizado`, `pojocollations`**
   - El `Main` y parte de `DAO` son casi idénticos.
   - Se puede compartir menú/base abstracta o factoría de DAOs.

6. **Mejorar validación de entrada**
   - Edad no negativa.
   - Campos obligatorios no vacíos.
   - Manejo de entradas nulas/espacios en listas de asignaturas.

## Prioridad baja

7. **Nomenclatura y pequeños detalles de calidad**
   - `crearColleccion` → `crearColeccion` (typo).
   - Mensajes de usuario consistentes (`Curso` en vez de `pojo.modelo.Curso`).
   - Eliminar imports no usados (`static Filters.eq` en `EstudianteDAO`).

8. **Índices recomendados en MongoDB**
   - `estudiantes.nombre` (si se consulta frecuentemente por nombre).
   - `cursos.codigo` único.

---

## 4) Ejemplo de refactor incremental recomendado

1. Introducir `AppConfig` para cargar URI/DB de entorno.
2. Cambiar `MongoManager.connect()` para recibir `AppConfig`.
3. Extraer de `operaciones.Main` un `MenuController`.
4. Añadir pruebas unitarias de parsing/validación (sin depender de Mongo real).
5. Añadir pruebas de integración opcionales con Testcontainers para MongoDB.

---

## 5) Conclusión

El proyecto cumple bien el objetivo formativo y ya contiene una base sólida de operaciones MongoDB. Las mejoras propuestas se centran en hacerlo más **mantenible, seguro y escalable**, sin perder su valor didáctico.

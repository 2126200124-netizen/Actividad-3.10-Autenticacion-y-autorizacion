# Documentación de perfiles y permisos

Sistema Escolar JavaFX — Autenticación y autorización

## 1. Cómo funciona

- **Login**: es la primera ventana. Acepta **ID de usuario o correo** y **contraseña**, y los valida contra la tabla `usuario`. Solo entran cuentas con estatus **Activo**.
- **Contraseñas**: se guardan con hash **PBKDF2-HMAC-SHA256** (120,000 iteraciones y sal aleatoria). Nunca en texto plano.
- **Sin registro**: por ser área administrativa no existe formulario de alta de cuentas; las cuentas se crean en la base de datos (`sql/seguridad.sql`).
- **Autorización**: al entrar se cargan los permisos del rol desde la tabla `permiso`. El menú deshabilita los módulos sin acceso; dentro de cada módulo se habilitan o deshabilitan los botones **Guardar** (crear), **Editar** (actualizar) y **Borrar** (eliminar). Si el perfil solo puede consultar, el formulario queda en modo solo lectura.
- **Cerrar sesión**: el botón *Cerrar sesión* registra la salida en la tabla `sesion`, cierra la ventana principal y regresa al login.
- **Bitácora**: cada inicio y cierre de sesión queda en la tabla `sesion` (`fecha_inicio`, `fecha_fin`) y se actualiza `ultimo_acceso` del usuario.

## 2. Los tres perfiles

### Administrador (rol 1)

Control total del sistema: administra todas las tablas (alta, consulta, modificación y baja).

Responsabilidades:

- Gestionar todos los catálogos y registros del sistema, incluido el personal administrativo.
- Dar de alta, corregir y dar de baja cualquier registro.
- Consultar el seguimiento académico (calificación + asistencia).

### Docente (rol 2)

Registra y corrige calificaciones y asistencias; consulta alumnos, materias, horarios y aulas.

Responsabilidades:

- Capturar y corregir calificaciones de los alumnos.
- Registrar y corregir la asistencia.
- Consultar alumnos, docentes, materias, departamentos, aulas, horarios y el seguimiento académico.
- No puede eliminar calificaciones ni asistencias, ni ver el personal administrativo.

### Servicios Escolares (rol 3)

Administra el catálogo escolar (alumnos, materias, aulas, horarios); solo consulta lo demás.

Responsabilidades:

- Mantener el catálogo escolar: alumnos, materias, aulas y horarios (alta, modificación y baja).
- Consultar docentes, departamentos, calificaciones, asistencia y seguimiento.
- No puede modificar calificaciones ni asistencias, ni ver el personal administrativo.

## 3. Permisos por tabla

C = Crear (Guardar) · R = Consultar · U = Actualizar (Editar) · D = Eliminar (Borrar) · — = sin permiso

| Tabla / módulo | Administrador | Docente | Servicios Escolares |
|---|:---:|:---:|:---:|
| Alumnos | C R U D | — R — — | C R U D |
| Docentes | C R U D | — R — — | — R — — |
| Administradores | C R U D | sin acceso | sin acceso |
| Materias | C R U D | — R — — | C R U D |
| Calificaciones | C R U D | C R U — | — R — — |
| Asistencia | C R U D | C R U — | — R — — |
| Seguimiento | — R — — | — R — — | — R — — |
| Departamentos | C R U D | — R — — | — R — — |
| Aulas | C R U D | — R — — | C R U D |
| Horarios | C R U D | — R — — | C R U D |

Nota: *Seguimiento* es una consulta (vista de calificación + asistencia), por eso solo tiene permiso de consulta.

## 4. Cuentas de prueba

| ID | Correo | Contraseña | Perfil | Estatus |
|---|---|---|---|---|
| 1 | admin@escuela.com | Admin123 | Administrador | Activo |
| 2 | docente@escuela.com | Docente123 | Docente | Activo |
| 3 | servicios@escuela.com | Servicios123 | Servicios Escolares | Activo |
| 4 | inactivo@escuela.com | Inactivo123 | Servicios Escolares | Inactivo |

La cuenta **Inactiva** sirve para demostrar que el login rechaza cuentas desactivadas.

## 5. Archivos principales

| Archivo | Función |
|---|---|
| `persistencia/UsuarioDAO.java` | Inicio de sesión: busca por ID o correo, verifica contraseña y estatus. |
| `persistencia/SesionDAO.java` | Registra el inicio y el cierre de sesión. |
| `persistencia/PermisoDAO.java` | Carga y valida los permisos del rol por tabla y acción. |
| `seguridad/Sesion.java` | Usuario y permisos de la sesión activa; `Sesion.puede(modulo, accion)`. |
| `seguridad/Contrasenas.java` | Hash y verificación de contraseñas (PBKDF2). |
| `controlador/FormularioBase.java` | Aplica los permisos a los botones y al modo solo lectura de cada CRUD. |
| `controlador/PrincipalController.java` | Deshabilita las opciones de menú sin acceso; cerrar sesión. |
| `controlador/AutenticacionController.java` | Pantalla de login. |

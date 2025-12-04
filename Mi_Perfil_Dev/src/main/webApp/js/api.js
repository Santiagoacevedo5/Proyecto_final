/**
 * Cliente API para comunicarse con los endpoints del servidor
 */

const API_BASE = '/mi-perfil-dev';

// ============ PERFIL ============

/**
 * Obtiene el perfil actual del servidor
 */
async function obtenerPerfil() {
    try {
        const response = await fetch(`${API_BASE}/perfil`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error al obtener perfil:', error);
        throw error;
    }
}

/**
 * Actualiza el perfil
 */
async function actualizarPerfil(perfil) {
    try {
        const response = await fetch(`${API_BASE}/perfil`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(perfil)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error al actualizar perfil:', error);
        throw error;
    }
}

/**
 * Elimina el perfil
 */
async function eliminarPerfil() {
    try {
        const response = await fetch(`${API_BASE}/perfil`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error al eliminar perfil:', error);
        throw error;
    }
}

// ============ HABILIDADES ============

/**
 * Obtiene todas las habilidades
 */
async function obtenerHabilidades() {
    try {
        const response = await fetch(`${API_BASE}/habilidades`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error al obtener habilidades:', error);
        return [];
    }
}

/**
 * Obtiene una habilidad específica por ID
 */
async function obtenerHabilidad(id) {
    try {
        const response = await fetch(`${API_BASE}/habilidades?id=${id}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error al obtener habilidad:', error);
        throw error;
    }
}

/**
 * Crea una nueva habilidad
 */
async function crearHabilidad(habilidad) {
    try {
        const response = await fetch(`${API_BASE}/habilidades`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(habilidad)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error al crear habilidad:', error);
        throw error;
    }
}

/**
 * Actualiza una habilidad existente
 */
async function actualizarHabilidad(habilidad) {
    try {
        const response = await fetch(`${API_BASE}/habilidades`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(habilidad)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error al actualizar habilidad:', error);
        throw error;
    }
}

/**
 * Elimina una habilidad
 */
async function eliminarHabilidad(id) {
    try {
        const response = await fetch(`${API_BASE}/habilidades?id=${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error al eliminar habilidad:', error);
        throw error;
    }
}
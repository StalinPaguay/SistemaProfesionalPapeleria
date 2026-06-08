import os
import re

base_dir = r"c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example"

class_pattern = re.compile(r'^(public\s+(class|interface|enum)\s+(\w+).*?\{)', re.MULTILINE)
method_pattern = re.compile(r'^(\s+)(public\s+[\w\<\>\[\]\s]+\s+(\w+)\s*\([^)]*\)\s*(?:throws\s+[\w\s,]+)?\s*[{;])', re.MULTILINE)

def has_javadoc_before(content, match_start):
    # Check if there's a javadoc comment before the match
    substring = content[:match_start].rstrip()
    return substring.endswith('*/')

def generate_class_javadoc(class_name, class_type):
    if class_type == 'interface':
        return f"/**\n * Interfaz que define las operaciones para {class_name}.\n */\n"
    elif class_type == 'enum':
        return f"/**\n * Enumeración {class_name}.\n */\n"
    else:
        if class_name.endswith("Controller"):
            return f"/**\n * Controlador REST para manejar las peticiones de {class_name.replace('Controller', '')}.\n */\n"
        elif class_name.endswith("ServiceImpl"):
            return f"/**\n * Implementación del servicio de {class_name.replace('ServiceImpl', '')}.\n */\n"
        elif class_name.endswith("Service"):
            return f"/**\n * Servicio para gestionar {class_name.replace('Service', '')}.\n */\n"
        elif class_name.endswith("Repository"):
            return f"/**\n * Repositorio de Spring Data JPA para {class_name.replace('Repository', '')}.\n */\n"
        elif class_name.endswith("DTO"):
            return f"/**\n * Objeto de Transferencia de Datos (DTO) para {class_name.replace('DTO', '')}.\n */\n"
        else:
            return f"/**\n * Entidad que representa a {class_name}.\n */\n"

def generate_method_javadoc(method_name, indent):
    if method_name.startswith("get"):
        return f"{indent}/**\n{indent} * Obtiene el valor.\n{indent} * @return el valor\n{indent} */\n"
    elif method_name.startswith("set"):
        return f"{indent}/**\n{indent} * Establece el valor.\n{indent} * @param valor el valor a establecer\n{indent} */\n"
    elif method_name == "save":
        return f"{indent}/**\n{indent} * Guarda o crea un registro.\n{indent} * @param dto el objeto a guardar\n{indent} * @return el objeto guardado\n{indent} */\n"
    elif method_name == "update":
        return f"{indent}/**\n{indent} * Actualiza un registro existente.\n{indent} * @param id el ID del registro\n{indent} * @param dto los nuevos datos\n{indent} * @return el objeto actualizado\n{indent} */\n"
    elif method_name == "delete":
        return f"{indent}/**\n{indent} * Elimina un registro por su ID.\n{indent} * @param id el ID del registro a eliminar\n{indent} */\n"
    elif method_name == "findById":
        return f"{indent}/**\n{indent} * Busca un registro por su ID.\n{indent} * @param id el ID a buscar\n{indent} * @return el registro encontrado\n{indent} */\n"
    elif method_name == "findAll":
        return f"{indent}/**\n{indent} * Obtiene todos los registros.\n{indent} * @return una lista de registros\n{indent} */\n"
    else:
        return f"{indent}/**\n{indent} * Método {method_name}.\n{indent} */\n"

for root, _, files in os.walk(base_dir):
    for f in files:
        if f.endswith(".java"):
            file_path = os.path.join(root, f)
            with open(file_path, "r", encoding="utf-8") as file:
                content = file.read()
            
            original_content = content
            
            # Find class definitions
            for match in reversed(list(class_pattern.finditer(content))):
                if not has_javadoc_before(content, match.start()):
                    class_type = match.group(2)
                    class_name = match.group(3)
                    javadoc = generate_class_javadoc(class_name, class_type)
                    content = content[:match.start()] + javadoc + content[match.start():]
            
            # Find method definitions
            for match in reversed(list(method_pattern.finditer(content))):
                indent = match.group(1)
                method_name = match.group(3)
                
                # Exclude overriding methods roughly by checking if @Override is before
                substring = content[:match.start()].rstrip()
                if substring.endswith("@Override"):
                    continue
                    
                if not has_javadoc_before(content, match.start()):
                    # avoid adding javadoc to standard getters/setters if we want, but user said ALL public methods
                    javadoc = generate_method_javadoc(method_name, indent)
                    content = content[:match.start()] + javadoc + content[match.start():]

            if content != original_content:
                with open(file_path, "w", encoding="utf-8") as file:
                    file.write(content)

print("JavaDoc added to files.")

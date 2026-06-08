import os
import re

base_dir = r"c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example\modules"

def add_validations():
    for root, _, files in os.walk(base_dir):
        for f in files:
            if f.endswith(".java") and ("entity" in root or "dto" in root):
                file_path = os.path.join(root, f)
                with open(file_path, "r", encoding="utf-8") as file:
                    content = file.read()
                
                original_content = content
                
                # Check if it has private fields without @NotNull or @NotBlank
                # We will only add it to basic types for simplicity
                
                # Add import if missing
                if "import jakarta.validation.constraints" not in content:
                    import_stmt = "import jakarta.validation.constraints.*;"
                    content = re.sub(
                        r'(package\s+[\w\.]+;)',
                        r'\1\n\n' + import_stmt,
                        content,
                        count=1
                    )
                
                # Naive regex for private String without @NotBlank
                content = re.sub(r'(\s+)private\s+String\s+(\w+);', r'\1@NotBlank(message = "El campo \2 es obligatorio")\1private String \2;', content)
                
                # Naive regex for private numeric/object without @NotNull
                # excluding id
                content = re.sub(r'(\s+)private\s+(Integer|Long|Double|BigDecimal|LocalDate|LocalDateTime|Boolean)\s+(?!id\b)(\w+);', r'\1@NotNull(message = "El campo \3 es obligatorio")\1private \2 \3;', content)
                
                # Clean up double annotations if they existed before
                content = re.sub(r'@NotBlank.*?@NotBlank', r'@NotBlank', content, flags=re.DOTALL)
                content = re.sub(r'@NotNull.*?@NotNull', r'@NotNull', content, flags=re.DOTALL)

                if content != original_content:
                    with open(file_path, "w", encoding="utf-8") as file:
                        file.write(content)
                        print(f"Added validations to {f}")

add_validations()

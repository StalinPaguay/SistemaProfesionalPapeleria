import os
import re

base_dir = r"c:\Users\USUARIO\OneDrive\Escritorio\IntellJ Proyects\Papeleria_PF\backend\src\main\java\org\example"

# Rename file
old_file = os.path.join(base_dir, "security", "JwtUtil.java")
new_file = os.path.join(base_dir, "security", "JwtTokenProvider.java")

if os.path.exists(old_file):
    os.rename(old_file, new_file)

# Replace in all java files
all_java_files = []
for root, _, files in os.walk(base_dir):
    for f in files:
        if f.endswith(".java"):
            all_java_files.append(os.path.join(root, f))

for file_path in all_java_files:
    with open(file_path, "r", encoding="utf-8") as f:
        content = f.read()

    new_content = content.replace("JwtUtil", "JwtTokenProvider")
    new_content = new_content.replace("jwtUtil", "jwtTokenProvider")

    if new_content != content:
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(new_content)

print("Renamed JwtUtil to JwtTokenProvider.")

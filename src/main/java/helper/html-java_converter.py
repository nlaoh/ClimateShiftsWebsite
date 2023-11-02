# Open the HTML file for reading
with open(r"..\..\resources\mission.html", "r") as html_file:
    # Initialize an empty string to store the result
    html = ""
    
    # Read the file line by line
    for line in html_file:
        # Escape any double quotes in the line
        escaped_line = line.replace('"', r'\"')
        # Append the escaped line to the result string
        html += f'html += "{escaped_line.rstrip()}";\n'

# Print the resulting HTML string
print(html)
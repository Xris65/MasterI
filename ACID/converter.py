from bs4 import BeautifulSoup
import json
import urllib.request
url = 'https://www.labri.fr/perso/zemmari/acid/tds/td6/td06-sujet.html'
response = urllib.request.urlopen(url)
#  for local html file
# response = open("/Users/note/jupyter/notebook.html")
text = response.read()

soup = BeautifulSoup(text, 'lxml')
# see some of the html
print(soup.div)
dictionary = {'nbformat': 4, 'nbformat_minor': 1, 'cells': [], 'metadata': {}}
for d in soup.findAll("div"):
    if 'class' in d.attrs.keys():
        for clas in d.attrs["class"]:
            if clas in ["text_cell_render", "input_area"]:
                # code cell
                if clas == "input_area":
                    cell = {}
                    cell['metadata'] = {}
                    cell['outputs'] = []
                    cell['source'] = [d.get_text()]
                    cell['execution_count'] = None
                    cell['cell_type'] = 'code'
                    dictionary['cells'].append(cell)

                else:
                    cell = {}
                    cell['metadata'] = {}

                    cell['source'] = [d.decode_contents()]
                    cell['cell_type'] = 'markdown'
                    dictionary['cells'].append(cell)
open('notebook.ipynb', 'w').write(json.dumps(dictionary))


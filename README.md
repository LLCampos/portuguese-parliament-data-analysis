# Portuguese Parliament Data Analysis

`raw-data` was downloaded from the [official Portuguese parliament's website](https://www.parlamento.pt/Cidadania/Paginas/DARegistoBiografico.aspx).

Data was processed from Scala code at `data-processor` and `data-viz` contains some data manipulation and visualization done through Python. 


### **Notes on `professions.csv`**

It was built by grouping the members of parliaments' professions in a bunch of categories that **I** though made sense.

If a member has more than one profession listed, the program picks one arbitrarily.

All categories/professions represented by only one person in a legislature are grouped in the category "Other".

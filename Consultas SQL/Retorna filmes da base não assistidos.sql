select f.id,f.titulo 
from Filmes as f
left join Filmes_Assistidos as fa
on f.imdbID = fa.imdbID
where fa.imdbID is null
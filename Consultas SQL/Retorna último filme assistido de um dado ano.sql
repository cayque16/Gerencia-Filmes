select * from Filmes_Assistidos 
where (posAno = 
(select max(posAno) from Filmes_Assistidos where dataAno = 2018)
and (dataAno = 2018))
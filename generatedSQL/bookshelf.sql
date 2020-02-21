insert into bookshelf
select librarycardno, 'favourites' as name, 0 as privacy
from patron
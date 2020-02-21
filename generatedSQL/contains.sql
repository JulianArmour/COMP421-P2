insert into contains (librarycardno, isbn, name)
select librarycardno, isbn, 'favourites' as name
from reviews
where rating >= 4
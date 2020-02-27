--patrons that do not have oustanding loans
-- patrons that can make a reservation (finebalance = 0)
select librarycardno
from patron
except
select librarycardno
from loan
where returndate is null;

-- books that can be reserved

select tb1.isbn, tb1.homebranch, checkoutdate+1 as reservationdate
from
    (
        --all books that are on loan
        select copyof as isbn, homebranch, checkoutdate
        from book_copy
                 inner join includes on book_copy.barcodeno = includes.barcodeno
                 inner join loan on includes.loanid = loan.loanid
        where status = 'on loan'
          and returndate is null --to select only curent loans
    )tb1
        left join
    (
        --the extra books that are available
        select copyof as isbn, homebranch
        from book_copy
        where status = 'available'
    )tb2
    on tb1.isbn = tb2.isbn and tb1.homebranch = tb2.homebranch
where tb2.isbn is null and tb2.homebranch is null -- this removes books that have an extra copy available



INSERT INTO reserves (librarycardno, isbn, pickupbranch, resstartdate,
                      resstatus, pickupdate)
VALUES (150700177, '9780385387132', 'Marie-Uguay Public Library',
        '2020-02-07', 0, null),
       (150700224, '9780671702434', 'Jean-Corbeil Public Library',
        '2020-02-15', 0, null),
       (150700308, '9783540307655', 'Père-Ambroise Public Library',
        '2020-02-15', 0, null),
       (150700364, '9781481443258', 'Parc-Extension Public Library',
        '2020-02-14', 0, null),
       (150700140, '9780985768225', 'Notre-Dame-de-Grâce Public Library',
        '2020-02-07', 0, null),
       (150700347, '9781557344090', 'Plateau-Mont-Royal Public Library',
        '2020-02-06', 0, null),
       (150700161, '9781426849190', 'Marc-Favreau Public Library',
        '2020-02-14', 0, null),
       (150700083, '9781439199312', 'Charleroi Public Library',
        '2020-02-18', 0, null),
       (150700312, '9780521379762', 'Belleville Public Library',
        '2020-02-19', 0, null)









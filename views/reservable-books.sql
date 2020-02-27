DROP VIEW reservable_books;
CREATE VIEW reservable_books AS
select tb1.isbn, tb1.homebranch, tb1.barcodeno, tb1.holdperiod
from
    (
        --all books that are on loan
        select copyof as isbn, homebranch, holdperiod, book_copy.barcodeno
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
order by isbn, homebranch
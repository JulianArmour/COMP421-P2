insert into payment (loanid, paymentdate, amountpaid, paymentmethod)
select loanid, returndate, sum((daysloaned - holdperiod) * dailyfinerate) as indvidualbookfine, 'debit'
from (
         select loan.loanid, returndate, dailyfinerate, holdperiod, (returndate - checkoutdate) as daysloaned
         from loan
                  inner join includes
                             on loan.loanid = includes.loanid
                  inner join book_copy
                             on includes.barcodeno = book_copy.barcodeno
         where returndate is not null
     )x
where (daysloaned - holdperiod) > 0
group by loanid, returndate
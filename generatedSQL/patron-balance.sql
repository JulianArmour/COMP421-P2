update patron
set finebalance = (
  select balance
  from (
         select fees.librarycardno,
                fees.charges - coalesce(payments.payments, money(0)) as balance
         from (
                select loan.librarycardno,
                       sum(
                           dailyfinerate
                           *
                           coalesce(((returndate - checkoutdate) - holdperiod),
                                    ((CURRENT_DATE - checkoutdate) - holdperiod))
                         ) as charges
                from loan
                       inner join includes
                                  on loan.loanid = includes.loanid
                       inner join book_copy
                                  on book_copy.barcodeno = includes.barcodeno
                where coalesce(
                        ((returndate - checkoutdate) - holdperiod),
                        ((CURRENT_DATE - checkoutdate) - holdperiod)
                        ) > 0
                group by loan.librarycardno
              ) fees
                left join (
           select loan.librarycardno, sum(amountpaid) as payments
           from payment
                  inner join loan
                             on payment.loanid = loan.loanid
           group by loan.librarycardno
         ) payments
                          on fees.librarycardno = payments.librarycardno
       ) balances
  where patron.librarycardno = balances.librarycardno
)
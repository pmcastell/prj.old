#Problem 1
def problem1():
    balance = 4842
    annualInterestRate = 0.2
    monthlyPaymentRate = 0.04
    
    month=1
    totalPaid=0
    while month<=12:
        monthlyPayment=monthlyPaymentRate*balance
        balance-=monthlyPayment
        totalPaid+=monthlyPayment
        balance+=annualInterestRate/12*balance
        print 'Month:',month
        print 'Minimum monthly payment:',round(monthlyPayment,2)
        print 'Remaining balance:',round(balance,2)
        month+=1
    print 'Total paid:',round(totalPaid,2)
    print 'Remaining balance:',round(balance,2)

#Problem 2
def problem2():
    balance = 3926
    annualInterestRate = 0.2
    
    
    monthlyPayment=0
    restBalance=balance
    while restBalance>0:
        monthlyPayment+=10
        restBalance=balance
        totalPaid=0.0
        month=1
        while month<=12:
            restBalance-=monthlyPayment
            totalPaid+=monthlyPayment
            restBalance+=annualInterestRate/12*restBalance
            #print 'Month:',month
            #print 'Minimum monthly payment:',round(monthlyPayment,2)
            #print 'Remaining balance:',round(balance,2)
            month+=1
    print 'Lowest Payment:',monthlyPayment


#Problem 3
balance = 999999
annualInterestRate = 0.18

MonthlyInterestRate = annualInterestRate / 12.0
lowerBound = balance / 12.0
upperBound = (balance * (1 + MonthlyInterestRate)**12) / 12.0 

monthlyPayment=0
restBalance=balance
while upperBound>lowerBound:
    monthlyPayment=(lowerBound+upperBound)/2.0
    restBalance=balance
    totalPaid=0.0
    month=1
    while month<=12:
        restBalance-=monthlyPayment
        totalPaid+=monthlyPayment
        restBalance+=annualInterestRate/12*restBalance
        month+=1
    if (abs(restBalance)<0.01):
        upperBound=lowerBound=monthlyPayment
    elif (restBalance>0):
        lowerBound=monthlyPayment
    else: # (restBalance<0):
        upperBound=monthlyPayment
        
print 'Lowest Payment:',round(monthlyPayment,2)

class BankAccount:
    def __init__(self, initial_balance):
        """Creates an account with the given balance."""
        self.balance=initial_balance
        self.fees=0
        print "Account created. Balance: "+str(self.balance)
        
    def deposit(self, amount):
        """Deposits the amount into the account."""
        self.balance+=amount
        print "Deposit: "+str(amount)+". Balance: "+str(self.balance)
        
    def withdraw(self, amount):
        """
        Withdraws the amount from the account.  Each withdrawal resulting in a
        negative balance also deducts a penalty fee of 5 dollars from the balance.
        """
        self.balance-=amount
        if (self.balance<0):
            self.fees+=5
            self.balance-=5
            print "Fee deducted 5. Balance: "+str(self.balance)
        print "Withdraw: "+str(amount)+". Balance: "+str(self.balance)
            
    def get_balance(self):
        """Returns the current balance in the account."""
        return self.balance
    
    def get_fees(self):
        """Returns the total fees ever deducted from the account."""
        return self.fees
        
fast=1
slow=1000
year=1
while(fast<slow):
    print "Year:",year,"Slow:",slow,"Fast:",fast
    slow*=2
    slow*=0.6
    fast*=2
    fast*=0.7
    year+=1

print "Year:",year,"Slow:",slow,"Fast:",fast
    
        
    

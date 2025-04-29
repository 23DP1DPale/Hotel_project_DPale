# Hotel Reservation System

The program allows users to choose two roles: manager or guest.
The manager is able to manage room reservations, availability, prices and also promotions. The guest can book rooms according to his needs.

### Interface

The main menu lets you to choose one of the roles: manager or guest.
#### guest
For Guest is shown various options: Book, Rooms, Balance, see your booked rooms and exit.

By choosing option "**Book**", program shows a table of rooms in hotel liks this:

================================================================================
Room         type          size         cost       discount    availability
================================================================================
room1        type1         size1        cost1     discount1    availability1
================================================================================
room2        type2         size3        cost4     discount5    availability6
================================================================================

and lets you input number of a room you would like to book. After that it asks for amount of nights to book the room. After book you are moved to main menu.


Choosing "**Rooms**" option program outputs table rooms similar to previous table, and shows options: Sort, Search and exit.

**Sort** option lets you to sort list of rooms by cost and size. Then you can choose how exactly to sort: from the lowest value to the most, and the other way around. After that it outputs a sorted table.

**Search** option lets you to search rooms by cost, size and discount. Choosing one of the options it lets you to enter range of value to search by. After that program outputs table of those rooms, which are equal to your searched values.

**Exit** returns you to main menu.


Choosing "**Balance**" option programm will show your balance and 3 options: deposit, withdraw, exit.
Deposit lets you to add money to your balance. Withdraw lets you to take out money from balance. Exit returns you to main menu.

Choosing option "**See your booked rooms**" program outputs a table with your booked rooms:

=========================================
Room   type   size   nights   total cost
=========================================
Room1  type1  size1    4      total cost
=========================================
Room2  type2  size2    5      total cost
=========================================

If you don't have any booked rooms, then program will output "**You don't have any booked rooms yet**".

Choosing option "**Exit**" makes program to end.

#### admin

The admin is shown these options: Cost, Availability, Discount, Exit.

Choosing "**Cost**" option program shows table of rooms and lets you to choose one of the rooms and change its cost.

Choosing "**Availability**" option program shows table of rooms again and now lets you to change availability of rooms. By choosing a room you have option to set this rooms availability to "available" or "unavailable".

Choosing "**Discount**" option program shows all rooms and lets you to set a discount for a certain room.

### Functions

Functions for guest role:

Functions for admin role:
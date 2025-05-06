# Hotel Reservation System

Hotel is a program which allows users to interact with hotel as a guest or an administrator.
The administrator is able to manage room reservations, availability and prices. The guest can book rooms according to his needs.

### Interface

The main menu lets you to choose one of the roles: admin or guest.

#### Guest

For Guest is shown a table of rooms in hotel like this:

================================================================================
Room         type          size         cost       discount    availability
================================================================================
room1        type1         size1        cost1     discount1    availability1
================================================================================
room2        type2         size3        cost4     discount5    availability6
================================================================================

Room - number of room (1, 2, 3, ...)
Type - type of room (single room, double room, suite, etc.)
Size - size of room (m²)
Cost - cost of room per night which includes discount (EUR)
Discount - discount for a room (%)
Availability - state of a room (available, unavailable, occupied)


Below table there are options: Book, Search and filter, Balance, View your booked rooms and Exit.


#### Admin

For Admin is shown a table of rooms like guest, but with additional total cost in hotel like this:

=========================================================================================
Room         type          size       totalCost     cost       discount    availability
=========================================================================================
room1        type1         size1      totalCost1    cost1     discount1    availability1
=========================================================================================
room2        type2         size3      totalCost2    cost4     discount5    availability6
=========================================================================================

Room - number of room (1, 2, 3, ...)
Type - type of room (single room, double room, suite, etc.)
Size - size of room (m²)
TotalCost - cost of a room per nigh with discount (EUR)
Cost - cost of room per night without discount (EUR)
Discount - discount for a room (%)
Availability - state of a room (available, unavailable, occupied)

Below the table is shown these options: Cost, Availability, Discount, Exit.


### Functions

#### Guest

By choosing option **Book** it lets you input number of the room you would like to book. Then, you will be asked to enter amount of nights for booking. After booking the room, you will be returned to the main menu.


**Rooms** option gives these options: Sort, Search and exit.

**Sort** option lets you to sort list of rooms by cost and size. Then you can choose how exactly to sort: from the lowest value to the most, and the other way around. After that it outputs a sorted table.

**Search** option lets you to search rooms by cost, size and discount. Choosing one of the options it lets you to enter range of value to search by. After that program outputs table of those rooms, which are equal to your searched values.

**Exit** returns you to main menu.


**Balance** option will show your balance and 3 options: deposit, withdraw, exit.
Deposit lets you to add money to your balance.
Withdraw lets you take out your money from balance.
Exit returns you to main menu.


Choosing option **View your booked rooms** program outputs a table with your bookings:

=========================================
Room   type   size   nights   total cost
=========================================
Room1  type1  size1    4      total cost
=========================================
Room2  type2  size2    5      total cost
=========================================

Room - number of room (1, 2, 3, ...)
Type - type of room (single room, double room, suite, etc.)
Size - size of room (m²)
Nights - amount of nights to stay
Total cost - cost of book (EUR)

If you don't have any booked rooms, then program will output "**No rooms booked**".


Choosing option **Exit** makes program to end.



#### Admin

Choosing **Cost** option program shows table of rooms and lets you to choose one of the rooms and change its cost.

Choosing **Availability** option program shows table of rooms again and now lets you to change availability of rooms. By choosing a room you have option to set this rooms availability to "available" or "unavailable".

Choosing **Discount** option program shows all rooms and lets you to set a discount for a certain room.

Choosing option **Exit** makes program to end.
## Travel around the world! 

*Have you dreamed about traveling around the world?*  
*How many countries have you been to? Do you have a bucket list for your future travelling?*  

**Your personal travelling robo-agent is here to help!**  

I remember your travel records and your bucket list for future travelling.  
I help you to track the cost spent on past travels and money you need to save for future travels.  


**Tell me your wishes and remember to notify me after each travel! Let's go~**  


---  


This project tracks travel history.  
Any travellers can use this to track their budgets.  
I am interested in this project because I want to travel around the world!  


---  



### Phase 2 

- As a user, I want to be able to add multiple countries to my travel list.  
- As a user, I want to be able to view my travel bucket list and visited list.  
- As a user, I want to update my travel record after I completed my trip.  
    - This means adds the country to the completed list, and removes the country from bucket list.  
- As a user, I want to be able to see the number of countries and costs associated with my bucket list and visited list.

- As a user, I want to be able to save my travel lists to a file.
- As a user, when I start the application, I want to be given the option to load my travel list from a file.


### Phase 3
- As a user, I want to be able to add multiple countries to my travel list visually.  
- As a user, I want to be able to load and save the state of the application visually.
- As a user, I want to update my travel record after I completed my trip visually.
    - This means adds the country to the completed list, and removes the country from bucket list visually.
- As a user, I want to be able to see the number of countries and costs for my travel record visually.


### Phase 4: Task 2
Wed Nov 24 17:21:06 PST 2021    Country(Belgium) has been added to the bucket list.
Wed Nov 24 17:21:06 PST 2021    Country(Germany) has been added to the bucket list.
Wed Nov 24 17:21:06 PST 2021    Country(Brazil) has been added to the bucket list.
Wed Nov 24 17:21:06 PST 2021    Country(Grace) has been added to the bucket list.
Wed Nov 24 17:21:06 PST 2021    Country(Canada) has been added to the visited list.
Wed Nov 24 17:21:06 PST 2021    Country(Russia) has been added to the visited list.
Wed Nov 24 17:21:06 PST 2021    Country(Japan) has been added to the visited list.
Wed Nov 24 17:21:06 PST 2021    Country(Thailand) has been added to the visited list.
Wed Nov 24 17:21:06 PST 2021    Country(Iceland) has been added to the visited list.
Wed Nov 24 17:21:06 PST 2021    Country(Cuba) has been added to the visited list.
Wed Nov 24 17:22:11 PST 2021    Country(Egypt) has been added to the bucket list.
Wed Nov 24 17:22:47 PST 2021    Country(Spain) has been added to the visited list.
Wed Nov 24 17:22:52 PST 2021    Country(Egypt) has been removed from the bucket list.
Wed Nov 24 17:22:52 PST 2021    Country(Egypt) has been added to the visited list.
Wed Nov 24 17:22:56 PST 2021    UI content has been saved to file.


### Phase 4: Task 3
In the current design, class TravelList hosts two lists, each contains multiple countries.  

Modification 1:  
I would do is to introduce a class to be a generic list of countries.   
Then a BucketList extending GenericList, a VisitedList extending GenericList.   
Each of these class will need to implement their own version or override method addCountry.   
BucketList will have one more deleteCountry method.  

Modification 2:  
I would change the class type from List to Set because no duplicate country is allowed in the classes.
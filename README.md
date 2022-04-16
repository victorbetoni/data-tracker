# DataTracker
### 🎲 Centralized data tracking API for Bukkit - Track and access any type of data from any plugin
###### ⚠️ This project has been discontinued. Feel free to fork and do whatever you want with it ⚠️

DataTracker is a plugin API which allows you to storage, retrieve and track your player's data in a centralized way

### Alright, but how can I implement it

##### 1 - Create a data tracking key 
Declare a data tracking key object somewhere in your code:

```java
public static DataTrackingKey<Integer> DEATHS_KEY = new DataTrackingKey<Integer>("deaths", 
    new DataTracker<Integer>((event) -> 1, Integer::sum), false, Integer.class);
```

Data tracking keys are used to reference to any kind of data being tracked, e.g., kills, deaths or whatever you want to track. They receive a generic param 
(DataTrackingKey<DATA>) which defines what type of data this key will track. In this example, we want to track the player's death count, so it will be an
integer value. We, also, need to define an identifier for this key ("deaths", in this example);
  
Now we're going to implement 2 kinds of functions: a adapter and a joiner.

##### Adapter
  
Adapter tells your key how you want your data to be adapted from the incoming event. Every adapter receive one parameter: a DataTrackingEvent. Inside your adapter
function you're going to convert your event to the data type of your key. In this case, we want to convert the DataTrackingEvent into an integer value. Since, in my
plugin context, I call one DataTrackingEvent everytime a player die, I just gonna convert it into 1, since: event called -> 1 death to it's target. You can modify this
logic as you want.
  
##### Joiner
  
Now that we have our adapter defined, we need to tell to our key how we want to merge the incoming data with the stored data. In this case, I just want to sum
them up. Joiner receive 2 parameters: the stored data, the incoming data and returns the result of the their merging.
  
Alright, now we just need to specify our data type class. Integer.class in this case.
  
##### 2 - Register your data tracking key
  
Inside your onEnable() method register your data tracking key with the following line:
  
```java
SingleDataTrackerController.INSTANCE.registerTracker(<your data tracking key>);
```
  
Keep in mind that this code must be called before your onEnable() method finish its life cycle, otherwise the data that belongs to this key wont be loaded.
  
##### 1 - Call you DataTrackingEvent

Now we just gonna implement our listener and call our DataTrackingEvent inside it
```java
@EventHandler
public void onDeath(PlayerDeathEvent event) {
  Bukkit.getPluginManager().callEvent(new DataTrackEvent("deaths", event.getEntity().getName(), new HashMap<>()));
}  
```
It's simple, I want to call a DataTrackEvent everytime a player die. You can adapt this. DataTrackEvents can be called from anywhere inside your code.
You can also pass a HashMap object as a context for your event with valuable informations you might want to consider when adapting your data.


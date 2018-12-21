package planetwars.strategies;
import planetwars.publicapi.*;

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.lang.Math.*;

//class for Strategy
public class My_Strategy implements IStrategy {

    //Creates and returns a stack list of all visible edges
    public Stack<IEdge> StackEdgeList(List<IVisiblePlanet> ConqueredVisiblePlanets) {
        Stack<IEdge> edgeStackList = new Stack<>();
        for (IPlanet planet : ConqueredVisiblePlanets) { // Loops through visible conquered planets
            Set<IEdge> edgesSet = planet.getEdges();
            for (IEdge edge : edgesSet) {   //get the edges of visible conquered planets
                edgeStackList.push(edge); //put into stack list
            }
        }
        return edgeStackList;
    }

    //Sorts edges length from min to max in stack list and returns it as a stack.
    public Stack<IEdge> sortedStackList(Stack<IEdge> StackList){
        Stack<IEdge> tempstack = new Stack<>();     //make new stack
        while(!StackList.empty()){
            IEdge edgeTemp = StackList.peek();  //temp edge, get the top of stack
            StackList.pop();
            while(!tempstack.empty() && tempstack.peek().getLength() > edgeTemp.getLength()){   //if top of temp stack is greater than edgeTemp
                StackList.push(tempstack.peek());   //push top of tempstack to stacklist
                tempstack.pop();    //remove
            }
            tempstack.push(edgeTemp);   //push edge temp to our tempstack
        }
        return tempstack;   //finally, return temp stack which is our new sorted stack list
    }

    //Creates a Hashmap for all destination planets
    public HashMap<Integer, IVisiblePlanet> DestinationHashMap(List<IVisiblePlanet> nonConqueredVisiblePlanets, List<IVisiblePlanet> conqueredVisiblePlanets) {
        HashMap<Integer, IVisiblePlanet> destinationHashMap = new HashMap<>();
        for (IVisiblePlanet Planet : nonConqueredVisiblePlanets) { //Loops through non-conquer planets and add them into hashmap
            destinationHashMap.put(Planet.getId(), Planet);
        }
        for (IVisiblePlanet Planet : conqueredVisiblePlanets) { //Loops through conquer planets and add them into hashmap
            destinationHashMap.put(Planet.getId(), Planet);
        }
        return destinationHashMap; //Returns hashmap
    }

    //Returns true if a destination planet is connected to an enemy planet.
    public boolean SendHelp(IVisiblePlanet Planet,HashMap<Integer, IVisiblePlanet> Map){
        Set<IEdge> edgeList = Planet.getEdges();
        boolean sendhelp = false;
        for(IEdge edge : edgeList){ //Loops through each edges and see if destination planet is own by enemy
            int ID = edge.getDestinationPlanetId();
            IVisiblePlanet destinationPlanet = Map.get(ID);
            if(destinationPlanet.getOwner() == Owner.OPPONENT){ //If destination planet is own by enemy, returns true
                sendhelp = true;
            }
        }

        //Returns false if none of the edges has a destination planet own by an enemy
        return sendhelp;
    }

    //Main method for executing strategy per turn
    public void takeTurn(List<IPlanet> planets, IPlanetOperations planetOperations, Queue<IEvent> eventsToExecute) {
        //Instants Array lists for all of conquered visible planets, nonConquered Visible Planets, and invisible planets each.
        List<IVisiblePlanet> conqueredVisiblePlanets = new ArrayList<>();
        List<IVisiblePlanet> nonConqueredVisiblePlanets = new ArrayList<>();
        List<IPlanet> InvisiblePlanets = new ArrayList<>();

        //Loops through each planet and place into the array list above according to their conquered or not status and their visibility status.
        for (IPlanet planet : planets) {
            if (planet instanceof IVisiblePlanet && ((IVisiblePlanet) planet).getOwner() == Owner.SELF) { //if planet is visible and own by player then add it to the conquer array
                conqueredVisiblePlanets.add((IVisiblePlanet) planet);
            } else if (planet instanceof IVisiblePlanet) { //if planet is not own by us, then put it into the non-conquer array list
                nonConqueredVisiblePlanets.add((IVisiblePlanet) planet);
            } else {
                InvisiblePlanets.add((planet)); //else add the planet into the invisible planet array list
            }
        }

        //Creates Hashmap for all visible destination using the DestinationHashMap( method above
        HashMap<Integer, IVisiblePlanet> HashMapID = DestinationHashMap(conqueredVisiblePlanets, nonConqueredVisiblePlanets);

        //Creates stack for edges and then sorts them using sortedStackList method
        Stack<IEdge> edgeStackList = StackEdgeList(conqueredVisiblePlanets);
        Stack<IEdge> SortedStack = sortedStackList(edgeStackList);

        //Loops through each visible edge and perform actions base on conditions
        for (IEdge edge : SortedStack) {

            //Instants edge's source and destination planets' ID and planet object
            int sourcePlanetID = edge.getSourcePlanetId();
            int destinationPlanetID = edge.getDestinationPlanetId();
            IVisiblePlanet sourcePlanet = HashMapID.get(sourcePlanetID);
            IVisiblePlanet destinationPlanet = HashMapID.get(destinationPlanetID);

            //if destination planet object owner is neutral, then send colony ship from source to destination.
            if (destinationPlanet.getOwner() == Owner.NEUTRAL && sourcePlanet.getPopulation() > 0) {
                long numPeople = (int) (sourcePlanet.getPopulation() * .50); //instants the
                eventsToExecute.offer(planetOperations.transferPeople(sourcePlanet, destinationPlanet, numPeople));
            }
            //if statement for when destination planet object owner is an enemy
            else if (destinationPlanet.getOwner() == Owner.OPPONENT) {
                long nextPop = (long) (destinationPlanet.getPopulation() * (1 + (destinationPlanet.getHabitability() / 100.0)));
                long popFinal = nextPop;
                //
                if (edge.getLength() > 1) { //if length of edge is greater than 1 then calculate the next population of destination planet depending on how long it takes to get there
                    for (int i = 1; i < edge.getLength(); i++) {
                        popFinal = (long) (popFinal * (1 + (destinationPlanet.getHabitability() / 100.0)));
                    }
                }
                //If source planet pop. is larger then enemy planet pop. then sent colony to enemy planet
                if (sourcePlanet.getPopulation() > popFinal) {
                    long numPeople = (long) (sourcePlanet.getPopulation() * .25);
                    eventsToExecute.offer(planetOperations.transferPeople(sourcePlanet, destinationPlanet, numPeople));
                }
            }
            //if statement for when destination planet is own by player
            else if (destinationPlanet.getOwner() == Owner.SELF) {

                //Instants needHelp to determine whether the destination planet is connected to a enemy own planet
                boolean needHelp = SendHelp(destinationPlanet, HashMapID);

                //if destinationPlanet is connected to enemy planet then source planet will send reinforcement to destination planet
                if (needHelp == true && sourcePlanet.getPopulation()/(sourcePlanet.getSize()) > .25) {
                    long numPeople = (long) (sourcePlanet.getPopulation() * .25);
                    eventsToExecute.offer(planetOperations.transferPeople(sourcePlanet, destinationPlanet, numPeople));

                    //Instants a set for all edges for the source planet
                    Set<IEdge> edges = sourcePlanet.getEdges();

                    //Loops through set of edges and makes destination planets send colony to source planet if planets population are greater than 20.
                    for(IEdge ed : edges) {
                        int ID = ed.getDestinationPlanetId();
                        IVisiblePlanet Planet = HashMapID.get(ID); //get Planet object

                        //If statement to determine when to send reinforcement from destination planet to source planet
                        if (ID != destinationPlanet.getId() && Planet.getOwner() == Owner.SELF && Planet.getPopulation() > 20) {
                            eventsToExecute.offer(planetOperations.transferPeople(Planet, sourcePlanet, numPeople));
                        }
                    }
                }
            }
        }

    }
    @Override
    //Method to return programmer names
    public String getName () {
        return "Dennis and Ken";
    }

    @Override
    //Method to return true if used
    public boolean compete () {
        return false;
    }
}
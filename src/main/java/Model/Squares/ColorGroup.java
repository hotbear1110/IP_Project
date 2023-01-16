package Model.Squares;

import java.util.ArrayList;

public class ColorGroup {
    private final String name;
    private final ArrayList<Property> property = new ArrayList<Property>();

    public ColorGroup(String name){
        this.name = name;
    }

    /**
     * This adds a property to the colorgroup array
     * @param site (Property) The property to be added to the colorgroup
     */
    public void addMember(Property site){
        property.add(site);
    }

    /**
     * @return (ArrayList<Property>) an array of all the properties for this colorgroup
     */
    public ArrayList<Property> getMembers(){
        return property;
    }

    /**
     * @return (String) the name of the colorgroup
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @return (int) The size/length of the colorgroup array (the amount of properties that is assigned to this colorgroup)
     */
    public int getSize(){
        return property.size();
    }
}

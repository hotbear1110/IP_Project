package Model.Squares;
import java.awt.*;
import java.util.ArrayList;

    public class ColorGroups {
        private final String name; //The name of the colorgroup
        private final Color color;
        private final ArrayList<Property> property = new ArrayList<Property>(); //An array of the properties that is assigned to this colorgroup

        //The constructor for the colorgroup

        /**
         *
         * @param name (String) The name of the colorgroup
         */
        public ColorGroups(String name, Color color){
            this.name = name;
            this.color = color;
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

        public Color getColor(){
            return this.color;
        }

        /**
         *
         * @return (int) The size/length of the colorgroup array (the amount of properties that is assigned to this colorgroup)
         */
        public int getSize(){
            return property.size();
        }
    }

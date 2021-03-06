public class ResizeableArrayBag<T> implements BagInterface<T> {
    private T[] array;
    private static final int INITIAL_CONPACITY = 10;
    private static final int MAX_CONPACITY = 1000;
    private boolean integrityOK = false;
    private int counts;

    private void resize() {
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Object[INITIAL_CONPACITY * 2];
        int j = 0;

        for (int i = 0; i < counts; i++) {
            if (j < counts) {
                temp[i] = array[j];
                j++;
            }
        }
        array = temp;
    }
    /**
     * Initialize constructor
     */
    public ResizeableArrayBag(){
        if(INITIAL_CONPACITY <= MAX_CONPACITY){
            @SuppressWarnings("unchecked")
            T[] tempBag = (T[])new Object[INITIAL_CONPACITY];
            counts = 0;
            array = tempBag;
            integrityOK = true;
        }
        else
            throw new IllegalStateException("THE CAPACITY IS EXCEED THE LITMID;");
        
    }
    
    @Override
    /**
     * Gets the current number of entries in this bag.
     * @return The integer number of entries currently in the bag.
     */
    public int getCurrentSize(){
        return counts;
        
    }

    /**
     * Sees whether this bag is full
     * @return True if the bag is full
     */
    public boolean isFull(){
        return array.length == counts;
    }

    @Override
    /**
     * Sees whether this bag is empty
     * @return True if the bag is empty
     */
    public boolean isEmpty(){
        return counts == 0;
    }

    @Override
    /**
     * add a object to the array
     * @param newEntry pass in the value
     * @return true if the addtion successful, or false if not
     */
    public boolean add(T newEntry){
        if(isEmpty()){
            array[counts] = newEntry;
            counts++;
            return true;
        }
        else
            if (isFull()){
                resize();
                array[counts] = newEntry;
                counts++;
                return true;
            }
        else
        {
            array[counts] = newEntry;
            counts++;
            return true;
        }
            
    }

    @Override
    /**
     * remove the first object from the array
     * 
     * @return the object that got romoved
     */
    public T remove(){
        T temp = array[0];
        
        array[0] = array[counts - 1];
        array[counts - 1] = null;

        return temp;
    }

    @Override
    /**
     * remove a given object from the array
     * @param newEntry pass in the object to remove
     * @return the object that got romoved
     */
    public boolean remove(T newEntry){
        T temp = null;
        boolean result = false;

        for(int i = 0; i < counts; i++){
            if(!isEmpty() && getFrequencyOf(newEntry) >= 1){
                if(newEntry.equals(array[i])){
                    temp = array[i];
                    array[i] = array[counts - 1];
                    array[counts - 1] = null; 
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    /**
     * clear the whole array
     */
    public void clear(){
        while(!isEmpty()){
            remove();
        }
    }

    @Override
    /**
     * count how many time that the given object has repeated
     * @param newEntry pass in to check the how many does it repeat
     * @return the count number
     */
    public int getFrequencyOf(T newEntry){
        int count = 0;
        
        for(int i = 0; i < counts; i++){
            if(newEntry.equals(array[i])){
                count++;
            }
        }
        return count;
    }

    @Override
    /**
     * to check if the number does exist in the array
     * 
     * @param newEntry pass in to check if the array has that number
     * @return true if the array does contain that number, or false if the array
     *         does not contain that number
     */
    public boolean contain(T newEntry){
        for (int i = 0; i < counts; i++){
            if(newEntry.equals(array[i])){
                return true;
            }
        }
        return false;
    }

    @Override
    /**
     *  retrieves the entries that are in the bag
     * @return a newly allocated array of all the entries in the bag
     */
    public T[] toArray(){
        @SuppressWarnings("unchecked")
        T[] result = (T[])new Object[counts];
        for(int i = 0; i < counts; i++){
            result[i] = array[i];
        }
        return result;
    }

    @Override
    /**
     * to combine two collection of datas into a new object
     * @param others object with the same class
     * @return a new object that contain the data from the class
     */
	public BagInterface<T> union(BagInterface<T> others) {
        ResizeableArrayBag<T> newBag = new ResizeableArrayBag<T>();
        T[] tempArray1 = this.toArray();
        T[] tempArray = ((ResizeableArrayBag<T>)others).toArray();
        int totalEntries = counts + ((ResizeableArrayBag<T>)others).getCurrentSize();
        int j = 0;

        if(others instanceof ResizeableArrayBag){
            for(int i = 0; i < totalEntries; i++){
                if(i < tempArray1.length){
                    newBag.add(tempArray1[i]);
                }
                else{
                    newBag.add(tempArray[j]);
                    j++;
                }
            }
        }
        
        return newBag;
    }

    @Override
    /**
     * to search the intersection of two data sets
     * @param others object with the same class
     * @return a new object of the same class that contain the instersect data.
     */
    public BagInterface<T> intersect(BagInterface<T> others) {
        boolean[] counter = new boolean[((ResizeableArrayBag<T>) others).getCurrentSize()];
        ResizeableArrayBag<T> newBag = new ResizeableArrayBag<T>();
        T[] tempBag1 = this.toArray();
        T[] tempBag2 = others.toArray();

        if(others instanceof ResizeableArrayBag){
            for (int i = 0; i < counts; i++ ){
                for(int j = 0; j < (((ResizeableArrayBag<T>) others).getCurrentSize()); j++){
                    if(tempBag1[i].equals(tempBag2[j]) && counter[j] == false){
                        newBag.add(tempBag1[i]);
                        counter[j] = true;
                        break;
                    }
                }
            }
        }
        
        return newBag;
    }

    @Override
    /**
     * to search the difference of two data sets
     * @param others object wotj tje same class
     * @return a new object of the same class that contain the difference data.
     */
    public BagInterface<T> difference(BagInterface<T> others) {
        boolean[] counter = new boolean[((ResizeableArrayBag<T>) others).getCurrentSize()];
        ResizeableArrayBag<T> newBag = new ResizeableArrayBag<T>();
        T[] tempBag1 = this.toArray();
        T[] tempBag2 = others.toArray();
        boolean switchCheck;

        if(others instanceof ResizeableArrayBag){
            for (int i = 0; i < counts; i++ ){
                switchCheck = true;
                for(int j = 0; j < ((ResizeableArrayBag<T>) others).getCurrentSize(); j++){
                    if(tempBag1[i].equals(tempBag2[j]) && counter[j] == false){
                        counter[j] = true;
                        switchCheck = false;
                        break;
                    }
                }
    
                if(switchCheck){
                    newBag.add(tempBag1[i]);
                }
                
            }
    
        }
        
        return newBag;
       
    }

    
}

package com.meifute.core.lang;


import java.util.*;

/**
 * @author wzeng
 * @date 2020/7/22
 * @Description
 */
public final class MftCollections {

    public final static  boolean isEmpty(Collection<?> coll){
        return coll == null || coll.isEmpty();
    }

    public final static boolean isNotEmpty(Collection<?> coll){
        return !isEmpty(coll);
    }

    /**
     * 并集 返回一个集合包含参数中两个Iterable的所有元素，如果一个元素同时在a，b 中出现多次，返回的集合中包含的次数是a b 中出现多的次数
     */
    public static <O> Collection<O> union(final Iterable<? extends O> a, final Iterable<? extends O> b) {

        final MftCollections.SetOperationCardinalityHelper<O> helper = new MftCollections.SetOperationCardinalityHelper<>(a, b);
        for (final O obj : helper) {
            helper.setCardinality(obj, helper.max(obj));
        }
        return helper.list();
    }

    /**
     * 交集， 返回的集合中一个元素出现的次数是其在 a，b 中出现次数少的次数。
     */
    public static <O> Collection<O> intersection(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        final MftCollections.SetOperationCardinalityHelper<O> helper = new MftCollections.SetOperationCardinalityHelper<>(a, b);
        for (final O obj : helper) {
            helper.setCardinality(obj, helper.min(obj));
        }
        return helper.list();
    }

    /**
     * 交集的补集合，元素出现的次数等于， 出现多的次数-出现少的次数。
     */
    public static <O> Collection<O> disjunction(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        final MftCollections.SetOperationCardinalityHelper<O> helper = new MftCollections.SetOperationCardinalityHelper<>(a, b);
        for (final O obj : helper) {
            helper.setCardinality(obj, helper.max(obj) - helper.min(obj));
        }
        return helper.list();
    }

    /**
     * 差集 其元素出现次数= a出现次数-b出现次数。如小于0 则不在结果中出现。
     */
    public static <O> Collection<O> subtract(final Iterable<? extends O> a, final Iterable<? extends O> b) {
        Map<O,Integer> map= countIterable(a);
        statMapMinusIterable(map,b);
        return statMapToCollection(map);
    }

    public static <O> Collection<O> statMapToCollection(Map<O,Integer> map){
        List<O> list=new LinkedList<>();
        map.forEach((k,v)->{
            for(int i=0;i<v;i++){
                list.add(k);
            }
        });
        return list;
    }

    public static <O> Map<O,Integer> countIterable(final Iterable<? extends O> iterable){
        Map<O,Integer> map = new HashMap<>();
        if(iterable!=null){
            for(O o:iterable){
                Integer count= map.get(o);
                if(count==null){
                    map.put(o,1);
                } else{
                    map.put(o,count+1);
                }
            }
        }
        return map;
    }

    public static <O> void statMapMinusIterable(Map<O,Integer> map, Iterable<? extends O> iterable){
        if(iterable==null){
            return;
        } else{
            for(O o:iterable){
                Integer count= map.get(o);
                if(count!=null){
                    if(count>1){
                        map.put(o,count-1);
                    } else if(count <= 1){
                        map.remove(o);
                    }
                }
            }
        }
    }

    public static <O> void statMapAddIterable(Map<O, Integer> map, Iterable<? extends O> iterable){
        if(iterable==null){
            return;
        } else{
            for(O o:iterable){
                Integer count= map.get(o);
                if(count==null){
                    map.put(o ,1);
                } else{
                    map.put(o ,count+1);
                }
            }
        }
    }

    public static <C> boolean addAll(final Collection<C> collection, final Iterable<? extends C> iterable) {
        if (iterable instanceof Collection<?>) {
            return collection.addAll((Collection<? extends C>) iterable);
        }
        return addAll(collection, iterable.iterator());
    }


    public static <C> boolean addAll(final Collection<C> collection, final Iterator<? extends C> iterator) {
        boolean changed = false;
        while (iterator.hasNext()) {
            changed |= collection.add(iterator.next());
        }
        return changed;
    }


    public static <C> boolean addAll(final Collection<C> collection, final Enumeration<? extends C> enumeration) {
        boolean changed = false;
        while (enumeration.hasMoreElements()) {
            changed |= collection.add(enumeration.nextElement());
        }
        return changed;
    }

    public static <C> boolean addAll(final Collection<C> collection, final C... elements) {
        boolean changed = false;
        for (final C element : elements) {
            changed |= collection.add(element);
        }
        return changed;
    }

    private static class SetOperationCardinalityHelper<O> extends MftCollections.CardinalityHelper<O> implements Iterable<O> {

        /** Contains the unique elements of the two collections. */
        private final Set<O> elements;

        /** Output collection. */
        private final List<O> newList;

        /**
         * Create a new set operation helper from the two collections.
         * @param a  the first collection
         * @param b  the second collection
         */
        public SetOperationCardinalityHelper(final Iterable<? extends O> a, final Iterable<? extends O> b) {
            super(a, b);
            elements = new HashSet<>();
            addAll(elements, a);
            addAll(elements, b);
            // the resulting list must contain at least each unique element, but may grow
            newList = new ArrayList<>(elements.size());
        }

        @Override
        public Iterator<O> iterator() {
            return elements.iterator();
        }

        /**
         * Add the object {@code count} times to the result collection.
         * @param obj  the object to add
         * @param count  the count
         */
        public void setCardinality(final O obj, final int count) {
            for (int i = 0; i < count; i++) {
                newList.add(obj);
            }
        }

        /**
         * Returns the resulting collection.
         * @return the result
         */
        public Collection<O> list() {
            return newList;
        }

    }

    private static class CardinalityHelper<O> {

        /** Contains the cardinality for each object in collection A. */
        final Map<O, Integer> cardinalityA;

        /** Contains the cardinality for each object in collection B. */
        final Map<O, Integer> cardinalityB;

        /**
         * Create a new CardinalityHelper for two collections.
         * @param a  the first collection
         * @param b  the second collection
         */
        public CardinalityHelper(final Iterable<? extends O> a, final Iterable<? extends O> b) {
            cardinalityA = MftCollections.<O>getCardinalityMap(a);
            cardinalityB = MftCollections.<O>getCardinalityMap(b);
        }

        /**
         * Returns the maximum frequency of an object.
         * @param obj  the object
         * @return the maximum frequency of the object
         */
        public final int max(final Object obj) {
            return Math.max(freqA(obj), freqB(obj));
        }

        /**
         * Returns the minimum frequency of an object.
         * @param obj  the object
         * @return the minimum frequency of the object
         */
        public final int min(final Object obj) {
            return Math.min(freqA(obj), freqB(obj));
        }

        /**
         * Returns the frequency of this object in collection A.
         * @param obj  the object
         * @return the frequency of the object in collection A
         */
        public int freqA(final Object obj) {
            return getFreq(obj, cardinalityA);
        }

        /**
         * Returns the frequency of this object in collection B.
         * @param obj  the object
         * @return the frequency of the object in collection B
         */
        public int freqB(final Object obj) {
            return getFreq(obj, cardinalityB);
        }

        private int getFreq(final Object obj, final Map<?, Integer> freqMap) {
            final Integer count = freqMap.get(obj);
            if (count != null) {
                return count.intValue();
            }
            return 0;
        }
    }
    public static <O> Map<O, Integer> getCardinalityMap(final Iterable<? extends O> coll) {
        final Map<O, Integer> count = new HashMap<>();
        for (final O obj : coll) {
            final Integer c = count.get(obj);
            if (c == null) {
                count.put(obj, Integer.valueOf(1));
            } else {
                count.put(obj, Integer.valueOf(c.intValue() + 1));
            }
        }
        return count;
    }
}

package ca.carleton.AmazinBookStore.User.BookRecommendation;

import ca.carleton.AmazinBookStore.Book.Book;
import ca.carleton.AmazinBookStore.Listing.Listing;
import ca.carleton.AmazinBookStore.User.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BookRecommendation {

    private double calculateJaccardIndex(User userOriginal, User userComparing) {
        List<Book> list1 = getPurchasedBooks(userOriginal);

        List<Book> list2 = getPurchasedBooks(userComparing);

        // convert the lists to sets
        HashSet<Book> set1 = new HashSet<Book>(list1);
        HashSet<Book> set2 = new HashSet<Book>(list2);

        // find the intersection and union of the two sets
        HashSet<Book> intersection = new HashSet<Book>(set1);
        intersection.retainAll(set2);

        HashSet<Book> union = new HashSet<Book>(set1);
        union.addAll(set2);

        // calculate the Jaccard index
        double jaccardIndex = (double) intersection.size() / union.size();

        return jaccardIndex;
    }

    private List<Book> getPurchasedBooks(User user){
        List<Book> list = new ArrayList<>();
        for (Listing listing: user.getPurchaseHistory()) {
            //list.add(listing.getBook());
        }
        return list;
    }

    private List<Book> difference(User userOriginal, User userComparing) {

        List<Book> list1 = getPurchasedBooks(userOriginal);

        List<Book> list2 = getPurchasedBooks(userComparing);

        // create a new list to store the difference
        List<Book> diff = new ArrayList<>();

        // iterate over each element in list2
        for (Book element : list2) {

            // if the element is not in list1, add it to the difference list
            if (!list1.contains(element)) {
                diff.add(element);
            }
        }

        return diff;
    }

    public List<Book> getRecommendation(User userOriginal, List<User> users){
        double highestValue = 0.0;
        List<Book> recommendation = new ArrayList<>();
        for (User userComparing: users) {
            if(calculateJaccardIndex(userOriginal, userComparing) > highestValue && calculateJaccardIndex(userOriginal, userComparing) != 1){
                recommendation = difference(userOriginal, userComparing);
            }
        }
        return recommendation;
    }
}

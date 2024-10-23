package com.crio.rentRead.config;

public class PathConstants {
//  Base Paths
    public static final String API_BASE_PATH = "/api/v1";
    public static final String USER_BASE_PATH = API_BASE_PATH + "/users";
    public static final String BOOK_BASE_PATH = API_BASE_PATH + "/books";

//  User Paths
    public static final String REGISTER_USER = USER_BASE_PATH + "/register";
    public static final String GET_ALL_USER = USER_BASE_PATH + "/all";
    public static final String GET_A_USER = USER_BASE_PATH + "/{userId}";
    public static final String UPDATE_USER_ROLE = USER_BASE_PATH + "/{userId}/role";

//  Book Paths
    public static final String REGISTER_BOOK = BOOK_BASE_PATH + "/register";
    public static final String GET_ALL_BOOK = BOOK_BASE_PATH + "/all";
    public static final String GET_A_BOOK = BOOK_BASE_PATH + "/{bookId}";

//  Rental Paths
    public static final String RENT_BOOK = BOOK_BASE_PATH + "/{bookId}/rent";
    public static final String RETURN_BOOK = BOOK_BASE_PATH + "/{bookId}/return";
}

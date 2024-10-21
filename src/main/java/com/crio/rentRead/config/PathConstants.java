package com.crio.rentRead.config;

public class PathConstants {
    public static final String API_BASE_PATH = "/api/v1";

// User Paths
    public static final String REGISTER_USER = "/user/register";
    public static final String LOGIN_USER = "/user/login";
    public static final String GET_ALL_USER = "/user";
    public static final String GET_A_USER = "/user/{userId}";

// Book Paths
    public static final String REGISTER_BOOK = "/book/register";
    public static final String GET_ALL_BOOK = "/book";
    public static final String GET_A_BOOK = "/book/{bookId}";

// Rental Paths
    public static final String RENT_BOOK = "/rental/rent";
    public static final String RETURN_BOOK = "/rental/return";
}

package com.netease.user.service;

import java.util.List;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.UserHouse;

public interface IUserHouseService {

	public void updateUserHouseInfo(UserHouse uhouse) throws ServiceException;

	public void insertUserHouse(UserHouse uhouse) throws ServiceException;

	public UserHouse getUserHouseById(int userId) throws ServiceException;

	public boolean deleteUserHouse(int userId) throws ServiceException;

	public List<String> saveBase64Image(int userId, List<String> images) throws ServiceException;

	public void addImage(int userId, List<String> images) throws ServiceException;

	public void deleteImage(int userId, List<String> photoNames);

}

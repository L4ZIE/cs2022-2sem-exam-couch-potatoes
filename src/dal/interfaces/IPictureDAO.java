package dal.interfaces;

import java.util.ArrayList;

public interface IPictureDAO {
    void addPicture(int id, String location, String refNumber);
    void deletePicture(int id);
    void createPicture(String path, String refNumber, int id);
    ArrayList<String> getAllPicturesForProject(String refNumber);
    int getMaxID();
    int getPictureIDByPath(String path);
}

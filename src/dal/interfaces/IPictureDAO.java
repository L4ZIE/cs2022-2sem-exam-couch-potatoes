package dal.interfaces;

import java.util.ArrayList;

public interface IPictureDAO {
    void addPicture(int id, String location, String refNumber);
    void deletePicture(int id);
    int getMaxID();
    ArrayList<String> getAllPicturesForProject(String refNumber);
}

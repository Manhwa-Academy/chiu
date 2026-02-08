package BUS;

import DAO.CategoryDAO;
import DTO.ThuocTinhSanPham.CategoryDTO;
import java.util.ArrayList;

public class CategoryBUS {

    private final CategoryDAO categoryDAO = new CategoryDAO();
    private ArrayList<CategoryDTO> listCategory = new ArrayList<>();

    public CategoryBUS() {
        listCategory = categoryDAO.selectAll();
    }

    public ArrayList<CategoryDTO> getAll() {
        return this.listCategory;
    }

    public CategoryDTO getByIndex(int index) {
        return this.listCategory.get(index);
    }

    public int getIndexByMaCategory(int macategory) {
        for (int i = 0; i < listCategory.size(); i++) {
            if (listCategory.get(i).getMacategory() == macategory) {
                return i;
            }
        }
        return -1;
    }

    public boolean add(CategoryDTO category) {
        boolean check = categoryDAO.insert(category) != 0;
        if (check) {
            this.listCategory.add(category);
        }
        return check;
    }

    public boolean delete(CategoryDTO category, int index) {
        boolean check = categoryDAO.delete(
                Integer.toString(category.getMacategory())) != 0;
        if (check) {
            this.listCategory.remove(index);
        }
        return check;
    }

    public boolean update(CategoryDTO category) {
        int index = getIndexByMaCategory(category.getMacategory());
        if (index == -1)
            return false;

        boolean check = categoryDAO.update(category) != 0;
        if (check) {
            this.listCategory.set(index, category);
        }
        return check;
    }

    public String[] getArrTenCategory() {
        String[] result = new String[listCategory.size()];
        for (int i = 0; i < listCategory.size(); i++) {
            result[i] = listCategory.get(i).getTencategory();
        }
        return result;
    }

    // Sửa phương thức getArrCategory để trả về danh sách tên category
    public String[] getArrCategory() {
        String[] categoryNames = new String[listCategory.size()];
        for (int i = 0; i < listCategory.size(); i++) {
            categoryNames[i] = listCategory.get(i).getTencategory(); // Thêm tên category vào danh sách
        }
        return categoryNames;
    }

    /** Check trùng tên category */
    public boolean checkDup(String name) {
        for (CategoryDTO c : listCategory) {
            if (c.getTencategory().equalsIgnoreCase(name.trim())) {
                return false;
            }
        }
        return true;
    }

    /** Đổ lên combobox */
    public String getTenCategory(int macategory) {
        int index = getIndexByMaCategory(macategory);
        if (index == -1) {
            return "";
        }
        return listCategory.get(index).getTencategory();
    }
}

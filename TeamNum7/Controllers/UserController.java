package Controllers;
import Models;

public class UserController {
	void Delete_user(User user) {
		//TODO delete user from users tables in database.
	}
	void Block_Costumer(Costumer user) {
		user.Status = 0;
	}
	void Edit_Cridet_Card(Costumer user,String NewCridet) {
		(Costumer)user.CridetCard=NewCreditCard;
	}
	void Add_User() {
		//TODO add the variables to the new user, the variables come from sign-up. 
		//user = new User()
	}
}

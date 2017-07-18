package demo;

public class Constants {
	/* define the website domain such as twitter or weibo */
	public static String namespace = "pinterest";
	/* define prefix of url */
	public static String im_url_prefix = "https://s-media-cache-ak0.pinimg.com/originals/";

	/* store category related interests */
	public static String category_related_interests = "category_related_interests";
	/* pin init info */
	public static String repinInitInfo = "repin_init_info";
	/* pin category info */
	public static String pinCateInfo = "pin_cate_info";
	/* user boards relation info */
	public static String relationUserBoardInfo = "relation_user_board";
	/* relation repin board info */
	public static String relationRepinBoardInfo = "relation_repin_board";
	/* temp relation pin board info */
	public static String tempRelationPinBoardInfo = "temp_relation_pin_board";
	/* relation pin board info */
	public static String relationPinBoardInfo = "relation_pin_board";
	/* relation selected pin board info */
	public static String selectedRePinBoardInfo = "selected_relation_pin_board"; 
	/* selected pin info */
	public static String selectedPinInfo = "selected_pin_info";
	/* repin info */
	public static String repinInfo = "repin_info";
	/* user pins info */
	public static String userPinsInfo = "user_pins_info";
	/* pin link info */
	public static String link_info = "link_info";
	public static String extracted_link_info = "extracted_link_info"; 
	/* page extract info */
	public static String page_extract_info = "page_extract_info";
	/* store category page */
	public static String category_info = "category_info";
	/* store user init url info */
	public static String userInitInfo = "user_init_info";
	/* store user init valid url info */
	public static String userValidInitInfo = "valid_user_init_info";
	/* store board init info */
	public static String boardInitInfo = "board_init_info";
	/* store selected board which are in 10 - 1000 */
	public static String selectedBoardInitInfo = "selected_board_init_info";
	/* valid board init info */
	public static String validBoardInitInfo = "valid_board_init_info";
	/* store board meta info */
	public static String boardMetaInfo = "board_meta_info";
	/* store link init info */
	public static String linkInitInfo = "link_init_info";
	/* store image init info */
	public static String imInitInfo = "im_init_info";
	/* store pin desrciption info */
	public static String pinDesInfo = "pin_des_info";
	/* store pin comments info */
	public static String pinComInfo = "pin_com_info";
	/* store pin images info */
	public static String pinImInfo = "pin_im_info";
	/* store user pins init info */
	public static String userPinsInitInfo = "user_pins_init_info";
	/* store user crawled or not*/
	public static String userCrawledInfo = "user_crawled_info";
	/*store user Basic info*/
	public static String userBasicInfo = "user_basic_info";
	/*store user basic board info*/
	public static String userBasicBoardsInfo = "user_basic_boards";
	/*store board Contents*/
	public static String boardContent = "user_boards";
	/*store user Following info*/
	public static String userFollowingInfo = "user_following";
	/*store user Follower info*/
	public static String userFollowerInfo = "user_follower";
	/*store user each board Follower info*/
	public static String userEachBoardFollowers = "user_eachBoard_follower";
	/*store user each collaborative user info*/ 
	public static String userEachBoardCollaborator = "user_eachBoard_collaborators";
	/*store user collective boards */
	public static String userCollaborativeBoardContents = "user_colla_boards"; 

	/* thread count */
	public static Integer threadCount = 400;
	public static Integer writingThreadCount = 1;
	/* define crawled user content limit range */
	public static Integer limitCount = 5000;
	/* read timeout */
	public static Integer readTimeOut = 100000;
	/* connection timeout */
	public static Integer connectionTimeOut = 100000;
	/* max connection time */
	public static Integer maxConnectionTime = 15;
	/* thread queue size */
	public static Integer threadQueueSize = 400;
	/* set MongoClient Max Connection Num*/
	public static Integer maxMongoConnectionTime = 5000;

	/* pinterest site */
	public static String pinterest_site = "https://www.pinterest.com/"; 

	//-------- experimental settings --------------
	public static Integer verified_users_no = 100;
	//check user following info
	public static String pinterest_ve_social = "pinterest_ve_social";
	//check comments and web page
	public static String pinterest_ve_textMap = "pinterest_ve_textMap";
	//check  verified number
	public static Integer ve_boards_no = 100;
	

	public static String COMMA_DELIMITER = ",";
	public static String line_separator = System.getProperty("line.separator");

}

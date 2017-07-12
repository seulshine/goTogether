package app.gotogether.com.go_together;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BossPage extends AppCompatActivity {

    static final int REQUEST_TAKE_ALBUM = 2002;
    static final int REQUEST_IMAGE_CROP = 2003;

    String mCurrentPhotoPath;
    Uri photoURI, albumURI;

    boolean isAlbum = false;

    EditText et;

    ArrayAdapter addressAdapter;
    ArrayList<MyData> addressList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_page);

        // 임시 팀원관리 리스트뷰
        addressList = new ArrayList<MyData>();
        addressList.add(new MyData("0", "최예슬"));
        addressList.add(new MyData("1", "정소연"));
        addressList.add(new MyData("2", "진슬기"));
        addressList.add(new MyData("3", "이유민"));

        addressAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, addressList);

        TextView num = (TextView) findViewById(R.id.textNum);
        TextView boss = (TextView) findViewById(R.id.bossName);

        num.setText("팀원 수 " + addressList.size());
        boss.setText("팀장 " + addressList.get(0).getName());

    }

    public void onClick (View v){
        switch (v.getId()){
            //case R.id.btnTest1 :
            //captureCamera();
            //break;
            case R.id.btnSet :
                getAlbum();
                break;
            case R.id.btnTitle :

                AlertDialog.Builder ad = new AlertDialog.Builder(BossPage.this);

                ad.setTitle("이름변경");       // 제목 설정
                ad.setMessage("변경할 이름을 입력해주세요");   // 내용 설정

                et = new EditText(this);
                ad.setView(et);


                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TextView text = (TextView) findViewById(R.id.title);
                        String title = et.getText().toString();

                        text.setText(title);

                        dialog.dismiss();
                    }
                });

                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        // Event
                    }


                });

                ad.show();

                break;

            case R.id.member :

                AlertDialog.Builder member = new AlertDialog.Builder(BossPage.this);

                member.setTitle("팀원관리");       // 제목 설정
                member.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                        // Event
                    }

                });
                member.setAdapter(addressAdapter, null);

                member.show();

                break;


        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/pathvalue/"+ imageFileName);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = storageDir.getAbsolutePath();
        Log.i("mCurrentPhotoPath", mCurrentPhotoPath);
        return storageDir;
    }

    public void getAlbum(){
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    public void cropImage(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(photoURI, "image/*");
        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기
        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
        //cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율
        //cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true);

        if(isAlbum == false) {
            cropIntent.putExtra("output", photoURI); // 크랍된 이미지를 해당 경로에 저장
        } else if(isAlbum == true){
            cropIntent.putExtra("output", albumURI); // 크랍된 이미지를 해당 경로에 저장
        }

        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    // 갤러리 새로고침, ACTION_MEDIA_MOUNTED는 하나의 폴더, FILE은 하나의 파일을 새로 고침할 때 사용함

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("onActivityResult", "CALL");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //case REQUEST_TAKE_PHOTO:
            //isAlbum = false;
            //cropImage();
            // break;

            case REQUEST_TAKE_ALBUM:
                isAlbum = true;
                File albumFile = null;
                try {
                    albumFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (albumFile != null) {
                    albumURI = Uri.fromFile(albumFile);
                }
                photoURI = data.getData();
                cropImage();
                break;

            case REQUEST_IMAGE_CROP:
                galleryAddPic();
                // 업로드
                //uploadFile(mCurrentPhotoPath);
                break;
        }
    }


}



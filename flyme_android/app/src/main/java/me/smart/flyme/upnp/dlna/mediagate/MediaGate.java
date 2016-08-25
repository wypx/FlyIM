/******************************************************************
* Usage:
 *  int mode = MediaGate.FILESYS_MODE;
    MediaGate mediaGate = new MediaGate(mode);
    mediaGate.start();
 *
******************************************************************/

package me.smart.flyme.upnp.dlna.mediagate;

import org.cybergarage.util.Debug;
import java.util.prefs.Preferences;
import me.smart.flyme.upnp.dlna.server.ConnectionManager;
import me.smart.flyme.upnp.dlna.server.ContentDirectory;
import me.smart.flyme.upnp.dlna.server.Directory;
import me.smart.flyme.upnp.dlna.server.MediaServer;
import me.smart.flyme.upnp.dlna.server.directory.file.FileDirectory;
import me.smart.flyme.upnp.dlna.server.directory.mythtv.MythDirectory;
import me.smart.flyme.upnp.dlna.server.object.format.DefaultFormat;
import me.smart.flyme.upnp.dlna.server.object.format.GIFFormat;
import me.smart.flyme.upnp.dlna.server.object.format.ID3Format;
import me.smart.flyme.upnp.dlna.server.object.format.JPEGFormat;
import me.smart.flyme.upnp.dlna.server.object.format.MPEGFormat;
import me.smart.flyme.upnp.dlna.server.object.format.PNGFormat;


public class MediaGate
{
    ////////////////////////////////////////////////
    // Constants
    ////////////////////////////////////////////////

    /**** Mode Option ****/
    public final static int MODE_OPT_MASK = 0x00FF;
    public final static int FILESYS_MODE = 0x0000;
    public final static int MYTHTV_MODE = 0x0001;

    /**** Support Option ****/
    public final static int SUPPORT_OPT_MASK = 0xFF00;
    public final static int FLASH_SUPPORT = 0x0100;

    public final static String MYTHTV_OPT_STRING_OLD = "-mythtv";
    public final static String MYTHTV_OPT_STRING = "--mythtv";
    public final static String VERBOSE_OPT_STRING = "-v";
    public final static String FLASH_OPT_STRING = "--flash";
    public static final String CONSOLE_OPT_STRING = "-console";

    ////////////////////////////////////////////////
    // Constructor
    ////////////////////////////////////////////////

    public MediaGate(int option)
    {
        try
        {
            mediaServ = new MediaServer(MediaServer.DESCRIPTION, ContentDirectory.SCPD, ConnectionManager.SCPD);
            setOption(option);

            switch (getModeOption())
            {
            case FILESYS_MODE:
                {
                    mediaServ.addPlugIn(new ID3Format());
                    mediaServ.addPlugIn(new GIFFormat());
                    mediaServ.addPlugIn(new JPEGFormat());
                    mediaServ.addPlugIn(new PNGFormat());
                    mediaServ.addPlugIn(new MPEGFormat());
                    loadUserDirectories();
                }
                break;
            case MYTHTV_MODE:
                {
                    mediaServ.addPlugIn(new DefaultFormat());
                    MythDirectory mythDir = new MythDirectory();
                    mediaServ.addContentDirectory(mythDir);
                }
                break;
            }


            if ( Debug.isOn() )
            {
                Debug.message("Starting in console mode");
                for (int n=0; n < mediaServ.getNContentDirectories(); n++)
                {
                    Directory dir = mediaServ.getContentDirectory(n);
                    Debug.message("serving content dir: " + dir.getFriendlyName());
                    int nItems = dir.getNContentNodes();
                    for (int x = 0; x < nItems; x++) {
                        Debug.message("\n" + dir.getNode(x));
                    }
                }

            }
        }
        catch (Exception e) {
            Debug.warning(e);
        }
    }

    ////////////////////////////////////////////////
    // Mode
    ////////////////////////////////////////////////

    private int option;

    private void setOption(int value)
    {
        option = value;
    }

    private int getOption()
    {
        return option;
    }

    private int getModeOption()
    {
        return (option & MODE_OPT_MASK);
    }

    private boolean isFileSystemMode()
    {
        return ((getModeOption() & MODE_OPT_MASK) == FILESYS_MODE) ? true : false;
    }

    private int getSupportOption()
    {
        return (option & SUPPORT_OPT_MASK);
    }

    ////////////////////////////////////////////////
    // Preferences (FileSystem)
    ////////////////////////////////////////////////

    private final static String DIRECTORY_PREFS_NAME = "directory";

    private Preferences prefs = null;

    private Preferences getUserPreferences()
    {
        if (prefs == null)
            prefs =	Preferences.userNodeForPackage(this.getClass());
        return prefs;
    }

    private Preferences getUserDirectoryPreferences()
    {
        return getUserPreferences().node(DIRECTORY_PREFS_NAME);
    }

    private void clearUserDirectoryPreferences()
    {
        try {
            Preferences dirPref = getUserDirectoryPreferences();
            String dirName[] = dirPref.keys();
            int dirCnt = dirName.length;
            for (int n=0; n<dirCnt; n++)
                dirPref.remove(dirName[n]);
        }
        catch (Exception e) {
            Debug.warning(e);
        }
    }

    private void loadUserDirectories()
    {
        try {
            Preferences dirPref = getUserDirectoryPreferences();
            String dirName[] = dirPref.keys();
            int dirCnt = dirName.length;
            Debug.message("Loadin Directories (" + dirCnt + ") ....");
            for (int n=0; n<dirCnt; n++) {
                String name = dirName[n];
                String path = dirPref.get(name, "");
                FileDirectory fileDir = new FileDirectory(name, path);
                getMediaServer().addContentDirectory(fileDir);
                Debug.message("[" + n + "] = " + name + "," + path);
            }
        }
        catch (Exception e)
        {
            Debug.warning(e);
        }
    }

    private void saveUserDirectories()
    {
        clearUserDirectoryPreferences();

        ContentDirectory conDir = getContentDirectory();
        try {
            Preferences dirPref = getUserDirectoryPreferences();
            int dirCnt = conDir.getNDirectories();
            for (int n=0; n<dirCnt; n++) {
                Directory dir = conDir.getDirectory(n);
                if (!(dir instanceof FileDirectory))
                    continue;
                FileDirectory fileDir = (FileDirectory)dir;
                dirPref.put(fileDir.getFriendlyName(), fileDir.getPath());
            }
        }
        catch (Exception e) {
            Debug.warning(e);
        }
    }

    ////////////////////////////////////////////////
    // MediaServer
    ////////////////////////////////////////////////

    private MediaServer mediaServ;

    public MediaServer getMediaServer()
    {
        return mediaServ;
    }

    public ContentDirectory getContentDirectory()
    {
        return mediaServ.getContentDirectory();
    }

    ////////////////////////////////////////////////
    // MediaServer
    ////////////////////////////////////////////////


    ////////////////////////////////////////////////
    // start/stop
    ////////////////////////////////////////////////

    public void start()
    {
        getMediaServer().start();
    }

    public void stop()
    {
        getMediaServer().stop();
        if (getOption() == FILESYS_MODE)
            saveUserDirectories();
    }

    ////////////////////////////////////////////////
    // Debug
    ////////////////////////////////////////////////

    public static void debug(MediaGate mgate)
    {
        /*
          String sortCriteria = "+dc:date,+dc:title,+upnp:class";
          mgate.getContentDirectory().sortContentNodeList(new ContentNodeList(), sortCriteria);
          */
    }

    ////////////////////////////////////////////////
    // main
    ////////////////////////////////////////////////

    public static void main(String args[])
    {
        Debug.off();

        boolean need_gui = true;
        int mode = FILESYS_MODE;
        Debug.message("args = " + args.length);
        for (int n=0; n<args.length; n++) {
            Debug.message("  [" + n + "] = " + args[n]);
            if (MYTHTV_OPT_STRING.compareTo(args[n]) == 0)
                mode = MYTHTV_MODE;
            if (MYTHTV_OPT_STRING_OLD.compareTo(args[n]) == 0)
                mode = MYTHTV_MODE;
            if (VERBOSE_OPT_STRING.compareTo(args[n]) == 0)
                Debug.on();
            if (CONSOLE_OPT_STRING.compareTo(args[n]) == 0)
                need_gui = false;
        }


        MediaGate mediaGate = new MediaGate(mode);
        debug(mediaGate);
        mediaGate.start();

        String name = null;
        String dir = "/sdcard";
        MediaGate mgate = null;
        MediaServer mserver = mgate.getMediaServer();
        FileDirectory fileDir = new FileDirectory(name, dir);
        mserver.getContentDirectory().addDirectory(fileDir);

        //deleteDirectory
        String dirStr = "/sdcard/";
        MediaGate mgate2 = null;
        MediaServer mserver2 = mgate.getMediaServer();
        mserver.getContentDirectory().removeDirectory(dirStr);

        //valueChanged
        int selIdx = 0;
        MediaServer mserver3 = null;
        Directory dir3 = mserver.getContentDirectory(selIdx);


        mgate.stop();
    }

}


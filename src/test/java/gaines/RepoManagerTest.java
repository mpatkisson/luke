package gaines;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Tests the RepoManager class
 * @author Mike Atkisson
 */
public class RepoManagerTest {

    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static File working = null;
    private Repository repo = null;
    private Git git = null;

    public RepoManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws IOException {
        // Creation of a temp folder that will contain the Git repository
        working = File.createTempFile("gaines-repo", "");
        working.delete();
        working.mkdirs();
        log.info("Working Directory Path : " + working.getAbsolutePath());
    }

    @Before
    public void setUp() throws IOException {
        // Create a Repository object
        repo = FileRepositoryBuilder.create(new File(working, ".git"));
        repo.create();
        git = new Git(repo);
    }

    @After
    public void tearDown() throws IOException {
        if (git != null) {
            git.close();
        }
        if (repo != null) {
            repo.close();
        }
        File gitDir = repo.getDirectory();
        FileUtils.deleteDirectory(gitDir);
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        FileUtils.deleteDirectory(working);
    }

    /**
     * Test of compareTo method, of class Task.
     */
    @Test
    public void canGetLogs() throws IOException, GitAPIException {
        // Create a new file and add it to the index
        File newFile = new File(working, "apples.txt");
        newFile.createNewFile();
        git.add().addFilepattern("apples.txt").call();

        // Now, we do the commit with a message
        git.commit().setAuthor("test", "test@example.com").setMessage("Testing canGetLogs").call();
        RepoManager manager = new RepoManager(working.getAbsolutePath());
        List<RevCommit> history = manager.getLog();
        log.info("Number of commits : " + history.size());
        Assert.assertTrue(history.size() == 1);
    }

}
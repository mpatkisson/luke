package gaines;

import com.google.common.collect.Lists;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.util.List;

/**
 * Assists with Git interaction
 */
public class RepoManager implements AutoCloseable {

    private FileRepository repo;
    private Git git;

    public RepoManager(String workDir) throws IOException {
        repo = new FileRepository(workDir + "/.git");
        git = new Git(repo);
    }

    public List<RevCommit> getLog() throws IOException, GitAPIException {
        ObjectId id = repo.resolve(Constants.HEAD);
        LogCommand command = git.log().add(id);
        Iterable<RevCommit> commits = command.call();
        return Lists.newArrayList(commits);
    }

    @Override
    public void close() throws Exception {
        if (git != null) {
            git.close();
        }
        if (repo != null) {
            repo.close();
        }
    }
}

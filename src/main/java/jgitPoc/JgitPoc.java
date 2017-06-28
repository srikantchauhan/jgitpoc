package jgitPoc;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.gitrepo.internal.RepoText;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.io.DisabledOutputStream;

public class JgitPoc {
    public static void main(String[] args) throws IOException, NoHeadException, GitAPIException {
    	Scanner in = new Scanner(System.in);
    	
    	System.out.println("Please enter absolute path of git repo e.g. /home/user/git/repoa");
    	String repoPath = in.nextLine();
    	
    	GitRepo repo = new GitRepo(repoPath);
    	
    	System.out.println("Please enter number of top commits to get");
    	int n = in.nextInt();
    	System.out.println("Do you want detailed information, enter 1 for Yes, 0 for No(default is No)");
    	int detailedInfo = 0;
    	detailedInfo = in.nextInt();
    	
    	System.out.println(detailedInfo);
    	repo.printLogs(n, detailedInfo);
    	
    }
    
    
}

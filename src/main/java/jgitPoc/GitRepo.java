package jgitPoc;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FS;

public class GitRepo {
	private Repository repo;
	private Git gitRepo;

	public GitRepo(String repoPath) {
		//check if a valid repository
		String repoPathFinal = repoPath +  "/.git";
		if (!RepositoryCache.FileKey.isGitRepository(new File(repoPathFinal), FS.DETECTED)) {
			System.out.println(repoPath + " is not a valid git repo.");
			System.exit(1); 
		}
		try {
			repo = new FileRepository(repoPathFinal);
			gitRepo = new Git(repo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printLogs(int n, int detailedInfo) {
		if (n <= 0) {
			n = 10;
		}
		try {
			System.out.println("Printing " + Integer.toString(n) + " most recent commits(excluding merge) information for " + repo.getBranch() + " branch");
			LogCommand logs = gitRepo.log();
			Iterable<RevCommit> commits = logs.setMaxCount(n).call();
			int x = 0;
			for (RevCommit commit : commits) {
				x++;
				System.out.println("#################### Commit Number " + Integer.toString(x) +" ####################");
	    		System.out.println("Commit Id : " + commit.getId().getName());
	    		System.out.println("Author Name : " + commit.getAuthorIdent().getName());
	    		System.out.println("Author Email : " + commit.getAuthorIdent().getEmailAddress());
	    		System.out.println("Commit Time : " + commit.getAuthorIdent().getWhen());
	    		System.out.println("Commit Message : " + commit.getFullMessage());
	    		
	    		
	    		//considering only ordinary commits for detailed information
	    		if (commit.getParentCount() == 1) {
	    			DiffFormatter d = new DiffFormatter(System.out);
	    			d.setRepository( repo );
	    			//comparting commit tree with its parent
	    			List<DiffEntry> entries = d.scan( commit.getParent(0), commit.getTree());
	    			

	    			for( DiffEntry entry : entries ) {
	    			  System.out.println("Operation Type : " +  entry.getChangeType() );
	    			  System.out.println("Old Path : " +  entry.getOldPath());
	    			  System.out.println("New Path : " +  entry.getNewPath());
	    			  
	    			  FileHeader fh = d.toFileHeader(entry);
	    			  
	    			  EditList ed = fh.toEditList();
	    			  
	    			  int linesAdded = 0;
	    			  int linesRemoved = 0;
	   
	    			  for (Edit e : ed) {
	    				  linesAdded += (e.getEndB() - e.getBeginB());
	    				  linesRemoved += (e.getEndA() - e.getBeginA());
	    			  }
	    			  System.out.println("Added : " +  Integer.toString(linesAdded) + ", Removed : " + Integer.toString(linesRemoved));
	    			  if (detailedInfo > 0) {
	    				  System.out.println("Printing Detailed info");
	    				  d.format(entry);
	    			  }
	    			  
	    			  System.out.println("\n");
	    			}
	    		}
	    		
//	    		RevTree t = commit.getTree();
//	    		TreeWalk tw = new TreeWalk(repo);
//	    		tw.reset();
//	    		tw.addTree(t);
//	    		while (tw.next()) {
//	    			System.out.println(tw.getNameString());
//	    		}
//	    		System.out.println(t.getName());
	    		System.out.println("\n");
	    		System.out.println("\n");
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

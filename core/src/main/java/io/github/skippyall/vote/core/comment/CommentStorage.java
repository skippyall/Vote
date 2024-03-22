package io.github.skippyall.vote.core.comment;

public interface CommentStorage extends Storage {
  Set<Long> getComments(long vote);
  Comment getComment(long id);
  void addComment(Vote vote, Comment comment);
  void removeComment(Comment comment);
  void removeComment(long id);

  Set<Comment> getComments(Vote vote)
}
